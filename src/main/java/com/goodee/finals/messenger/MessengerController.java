package com.goodee.finals.messenger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.goodee.finals.staff.StaffDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/msg/**") @Controller @Slf4j
public class MessengerController {
	
	@Autowired
	MessengerService messengerService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
    
    @GetMapping("")
    public String home(Model model) {
    	List<StaffDTO> result = messengerService.getStaff();
    	model.addAttribute("members", result);
    	return "messenger/home";
    }
	
	@GetMapping("room/{roomType}")
	public String pop(@PathVariable("roomType") String roomType, Model model) {
		List<ChatRoomDTO> result = messengerService.list(roomType);
		model.addAttribute("room", result);
		model.addAttribute("active", roomType);
		return "messenger/list";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		List<StaffDTO> result = messengerService.getStaff();
		model.addAttribute("staff", result);
		return "messenger/create";
	}
	
	@PostMapping("create")
	public String create(@RequestParam(required = false) List<Integer> addedStaff, @Valid ChatRoomDTO chatRoomDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<StaffDTO> result = messengerService.getStaff();
			model.addAttribute("staff", result);
			return "messenger/create";
		}
		ChatRoomDTO result = messengerService.createRoom(addedStaff, chatRoomDTO);
		model.addAttribute("resultMsg", "채팅방이 생성되었습니다.");
		model.addAttribute("resultIcon", "success");
		model.addAttribute("resultUrl", "msg/room");
		return "common/result";
	}
	
	@PostMapping("chat")
	public String chat(@PageableDefault(size = 20, sort = "chatBodyNum", direction= Sort.Direction.DESC) Pageable pageable, ChatRoomDTO chatRoomDTO, Model model) {
		Page<MessengerTestDTO> result = messengerService.chatList(pageable, chatRoomDTO.getChatRoomNum());
		List<MessengerTestDTO> messages = new ArrayList<>(result.getContent());
		for (MessengerTestDTO m : messages) {
			m.setChatDate(m.getChatBodyDtm().getMonthValue() + "월 " + m.getChatBodyDtm().getDayOfMonth() + "일");
			if (m.getChatBodyDtm().getMinute() < 10) {
				m.setChatTime(m.getChatBodyDtm().getHour() + ":0" + m.getChatBodyDtm().getMinute());				
			} else {				
				m.setChatTime(m.getChatBodyDtm().getHour() + ":" + m.getChatBodyDtm().getMinute());				
			}
		}
		messages.sort(Comparator.comparing(MessengerTestDTO::getChatBodyNum));
		model.addAttribute("chatRoomNum", chatRoomDTO.getChatRoomNum());
		model.addAttribute("chat", messages);
		model.addAttribute("next", result.hasNext());
		
		ChatRoomDTO chatRoomResult = messengerService.findChatRoom(chatRoomDTO);
		model.addAttribute("chatRoom", chatRoomResult);
		
		return "messenger/chat";
	}
	
	@PostMapping("load") @ResponseBody
	public Map<String, Object> load(@RequestBody Map<String, String> loadData, @PageableDefault(size = 20, sort = "chatBodyNum", direction= Sort.Direction.DESC) Pageable pageable) {
		Page<MessengerTestDTO> result = messengerService.chatList(PageRequest.of(Integer.parseInt(loadData.get("page")), pageable.getPageSize(), pageable.getSort()), Long.parseLong(loadData.get("chatRoomNum")));
		List<MessengerTestDTO> messages = new ArrayList<>(result.getContent());
		// 정렬
		// messages.sort(Comparator.comparing(MessengerTestDTO::getChatBodyNum));
		Map<String, Object> response = new HashMap<>();
		response.put("messages", messages);
		response.put("next", result.hasNext());
		return response;
	}
	
	@GetMapping("profile")
	public String profile(Integer staffCode, Model model) {
		StaffDTO result = messengerService.profile(staffCode);
		model.addAttribute("profile", result);
		return "messenger/profile";
	}
	
	@PostMapping("profile/chat")
	public String profile(StaffDTO staffDTO, @PageableDefault(size = 20, sort = "chatBodyNum", direction= Sort.Direction.DESC) Pageable pageable, Model model) {
		Integer sendStaffCode = staffDTO.getStaffCode();
		Map<String, Object> result = messengerService.linkOrCreate(sendStaffCode, pageable);
		boolean check = (boolean) result.get("check");
		if (check) {
			ChatRoomDTO checkTrueResult = (ChatRoomDTO)result.get("result");
			Page<MessengerTestDTO> resultIfNotPresent = messengerService.chatList(pageable, checkTrueResult.getChatRoomNum());
			List<MessengerTestDTO> messages = new ArrayList<>(resultIfNotPresent.getContent());
			messages.sort(Comparator.comparing(MessengerTestDTO::getChatBodyNum));
			model.addAttribute("chatRoomNum", checkTrueResult.getChatRoomNum());
			model.addAttribute("chat", messages);
			model.addAttribute("next", resultIfNotPresent.hasNext());
		} else {
			Page<MessengerTestDTO> resultIfPresent = (Page<MessengerTestDTO>)result.get("result");
			List<MessengerTestDTO> messages = new ArrayList<>(resultIfPresent.getContent());
			messages.sort(Comparator.comparing(MessengerTestDTO::getChatBodyNum));
			model.addAttribute("chatRoomNum", (Long)result.get("chatRoomNum"));
			model.addAttribute("chat", messages);
			model.addAttribute("next", resultIfPresent.hasNext());
		}
		return "messenger/chat";
	}
	
	@PostMapping("exit")
	public void unread(Long chatRoomNum, Integer staffCode) {
		// 브라우저가 종료될 때 비동기 작업이 보장이 되지 않으므로 백에서 처리 - 이 메서드에서 직접 웹소켓 메시지를 전송
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setType("NOUNREADCOUNT");
		notificationDTO.setMsg("DEPLETE");
		simpMessagingTemplate.convertAndSend("/sub/notify/" + staffCode, notificationDTO);
		messengerService.unread(chatRoomNum);
	}
	
	@PostMapping("unread/count") @ResponseBody
	public Map<String, Object> getUnreadCounts(@RequestBody List<Long> rooms) {
		Map<Long, Integer> unreadCountResult = new HashMap<>();
		Map<Long, MessengerTestDTO> latestMessageResult = new HashMap<>();
		for (Long r : rooms) {
			int count = messengerService.getUnreadCounts(r);
			MessengerTestDTO latest = messengerService.getLatestMessage(r);
			unreadCountResult.put(r, count);
			latestMessageResult.put(r, latest);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("unread", unreadCountResult);
		result.put("latest", latestMessageResult);
		return result;
	}
	
	@PostMapping("new") @ResponseBody
	public MessengerTestDTO newChat(@RequestBody MessengerTestDTO messengerTestDTO) {
		MessengerTestDTO result = messengerService.newChat(messengerTestDTO);
		return result;
	}
	
	@GetMapping("footer") @ResponseBody
	public List<ChatRoomDTO> footer() {
		List<ChatRoomDTO> result = messengerService.list("all");
		return result;
	} 
	
	@PostMapping("notify") @ResponseBody
	public List<ChatUserDTO> notify(@RequestBody ChatRoomDTO chatRoomDTO) {
		List<ChatUserDTO> result = messengerService.getNotify(chatRoomDTO);
		return result;
	}
	
	@PostMapping("/room/add") @ResponseBody
	public List<StaffDTO> memberJoin(@RequestBody ChatRoomDTO chatRoomDTO) {
		List<StaffDTO> result = messengerService.getStaffForGroupChat(chatRoomDTO);
		return result;
	}
	
	@PostMapping("/room/join") @ResponseBody
	public boolean memberJoin(@RequestBody Map<String, Object> data) {
		List<String> staffs = (List<String>)data.get("staffs");
		Long chatRoomNum = Long.parseLong(data.get("chatRoomNum") + "");
		int result = messengerService.joinMember(staffs, chatRoomNum);
		if (result > 0) return true;
		else return false;
	}
	
	@PostMapping("/room/leave") @ResponseBody
	public boolean memberLeave(@RequestBody ChatRoomDTO chatRoomDTO) {
		boolean result = messengerService.leaveMember(chatRoomDTO);
		return result;
	}
	
	@GetMapping("/room/groupMembers/{chatRoomNum}") @ResponseBody
	public List<ChatUserDTO> getGroupMembers(@PathVariable("chatRoomNum") ChatRoomDTO chatRoomDTO) {
		List<ChatUserDTO> response = null;
		List<ChatUserDTO> result = messengerService.getNotify(chatRoomDTO);
		if (result != null && result.size() > 0) {
			response = result;
		}
		return response;
	}
}

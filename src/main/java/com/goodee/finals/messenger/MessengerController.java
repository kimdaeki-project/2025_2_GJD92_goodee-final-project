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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.goodee.finals.staff.StaffDTO;

@RequestMapping("/msg/**") @Controller
public class MessengerController {
	
	@Autowired
	MessengerService messengerService;
    
    @GetMapping("")
    public String home(Model model) {
    	List<StaffDTO> result = messengerService.getStaff();
    	model.addAttribute("members", result);
    	return "messenger/home";
    }
	
	@GetMapping("room")
	public String pop(Model model) {
		List<ChatRoomDTO> result = messengerService.list();
		model.addAttribute("room", result);
		return "messenger/list";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		List<StaffDTO> result = messengerService.getStaff();
		model.addAttribute("staff", result);
		return "messenger/create";
	}
	
	@PostMapping("create")
	public String create(@RequestParam(required = false) List<Integer> addedStaff, ChatRoomDTO chatRoomDTO, Model model) {
		if (addedStaff.size() > 1) {
			chatRoomDTO.setChatRoomGroup(true);
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
		messages.sort(Comparator.comparing(MessengerTestDTO::getChatBodyNum));
		model.addAttribute("chatRoomNum", chatRoomDTO.getChatRoomNum());
		model.addAttribute("chat", messages);
		model.addAttribute("next", result.hasNext());
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
	public void unread(Long chatRoomNum) {
		messengerService.unread(chatRoomNum);
	}
	
	@PostMapping("unread/count") @ResponseBody
	public Map<Long, Integer> getUnreadCounts(@RequestBody List<Long> rooms) {
		Map<Long, Integer> result = new HashMap<>();
		for (Long r : rooms) {
			int count = messengerService.getUnreadCounts(r);
			result.put(r, count);
		}
		return result;
	}
	
}

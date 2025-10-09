package com.goodee.finals.messenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class MessengerService {
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired
	MessengerRepository messengerRepository;
	
	@Autowired
	StompRepository stompRepository;

	public List<StaffDTO> getStaff(String keyword) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaff = staffDTO.get().getStaffCode();
		List<StaffDTO> result = staffRepository.findByStaffCodeNotAndStaffNameContaining(loggedStaff, keyword);
		return result;
	}

	public ChatRoomDTO createRoom(List<Integer> addedStaff, ChatRoomDTO chatRoomDTO) {
		if (addedStaff.size() > 2) {
			chatRoomDTO.setChatRoomGroup(true);
			if (chatRoomDTO.getChatRoomName() == null || chatRoomDTO.getChatRoomName().equals("")) {
				chatRoomDTO.setChatRoomName("GROUP_NONAME");
			}
		} else {
			if (chatRoomDTO.getChatRoomName() == null || chatRoomDTO.getChatRoomName().equals("")) {
				chatRoomDTO.setChatRoomName("DM_NONAME");
			}
		}
		ChatRoomDTO savedRoom = messengerRepository.save(chatRoomDTO);
		
		savedRoom.setChatUserDTOs(new ArrayList<ChatUserDTO>());
		for (Integer staffCode : addedStaff) {
			ChatUserDTO chatUserDTO = new ChatUserDTO();
			Optional<StaffDTO> staffDTO = staffRepository.findById(staffCode);
			chatUserDTO.setStaffDTO(staffDTO.get());
			chatUserDTO.setChatRoomDTO(savedRoom);
			savedRoom.getChatUserDTOs().add(chatUserDTO);
		}

		ChatRoomDTO result = messengerRepository.save(savedRoom);
		return result;
		
	}

	public List<ChatRoomDTO> list(String roomType) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaff = staffDTO.get().getStaffCode();
		List<ChatRoomDTO> result = null;
		List<ChatRoomDTOProjection> comparator = null;
		if ("all".equals(roomType)) { // 모든 채팅을 전부 가져오기
			
			result = messengerRepository.findChatRoomByStaffCode(loggedStaff);
			comparator = messengerRepository.findMaxChatBodyNum(loggedStaff);
			
			List<ChatRoomDTO> chatRoomYesMsg = new ArrayList<>();
			List<ChatRoomDTO> chatRoomNoMsg = new ArrayList<>();
			for (ChatRoomDTOProjection c : comparator) {
				for (ChatRoomDTO cr : result) {
					if (cr.getChatRoomNum().equals(c.getChatRoomNum())) {
						cr.setChatRoomMax(c.getChatRoomMax());
						chatRoomYesMsg.add(cr);
					} else {
						chatRoomNoMsg.add(cr);
					}
				}
			}
			Collections.sort(chatRoomYesMsg, new Comparator<ChatRoomDTO>() {
				@Override
				public int compare(ChatRoomDTO o1, ChatRoomDTO o2) {
					return o2.getChatRoomMax().intValue() - o1.getChatRoomMax().intValue();
				}
			});
			for (ChatRoomDTO c : chatRoomYesMsg) {
				System.out.println(c.getChatRoomNum() + ": " + c.getChatRoomMax());
			}
		} else {
			boolean type = false; // 일단 1:1 채팅으로 세팅
			if ("group".equals(roomType)) { // 그룹 채팅으로 세팅
				type = true;
			}
			result = messengerRepository.findChatRoomByStaffCodeAndType(loggedStaff, type);
		}
		return result;
	}

	public MessengerTestDTO saveChat(MessengerTestDTO message) {
		MessengerTestDTO result = stompRepository.save(message);
		return result;
	}

	public Page<MessengerTestDTO> chatList(Pageable pageable, Long chatRoomNum) {
		Optional<StaffDTO> logged = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaff = logged.get().getStaffCode();
		ChatUserDTO chatUserDTO = messengerRepository.getLatestChat(chatRoomNum, loggedStaff);
		Long chatGroupLatest = chatUserDTO.getChatGroupLatest();
		
		Page<MessengerTestDTO> result = messengerRepository.chatList(pageable, chatRoomNum, chatGroupLatest);
		for (MessengerTestDTO m : result.getContent()) {
			Optional<StaffDTO> staffDTO = staffRepository.findById(m.getStaffCode());
			m.setStaffName(staffDTO.get().getStaffName());
		}
		return result;
	}

	public StaffDTO profile(Integer staffCode) {
		Optional<StaffDTO> result = staffRepository.findById(staffCode);
		return result.get();
	}

	public Map<String, Object> linkOrCreate(Integer sendStaffCode, Pageable pageable) {
		
		Map<String, Object> result = new HashMap<>();
		Long roomNumIfPresent = null;
		
		Optional<StaffDTO> staffDTOLogged = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaffCode = staffDTOLogged.get().getStaffCode();
		
		List<ChatRoomDTO> resultLogged = messengerRepository.findByChatRoomGroupFalseAndChatUserDTOsStaffDTOStaffCode(loggedStaffCode);
		List<ChatRoomDTO> resultSend = messengerRepository.findByChatRoomGroupFalseAndChatUserDTOsStaffDTOStaffCode(sendStaffCode);
		
		boolean flag = true;
		
		if (resultLogged != null && resultSend != null) {	
			out: for (ChatRoomDTO logged : resultLogged) {
				for (ChatRoomDTO send : resultSend) {
					if (logged.getChatRoomNum() == send.getChatRoomNum()) {
						flag = false;
						roomNumIfPresent = logged.getChatRoomNum();
						break out;
					}
				}
			}
		}
		
		if (flag) {
			ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
			chatRoomDTO.setChatRoomName("DM_NONAME");
			ChatRoomDTO savedRoom = messengerRepository.save(chatRoomDTO);
			List<Integer> addedStaff = new ArrayList<>();
			addedStaff.add(loggedStaffCode);
			addedStaff.add(sendStaffCode);
			
			savedRoom.setChatUserDTOs(new ArrayList<ChatUserDTO>());
			for (Integer staffCode : addedStaff) {
				ChatUserDTO chatUserDTO = new ChatUserDTO();
				Optional<StaffDTO> staffDTO = staffRepository.findById(staffCode);
				chatUserDTO.setStaffDTO(staffDTO.get());
				chatUserDTO.setChatRoomDTO(savedRoom);
				savedRoom.getChatUserDTOs().add(chatUserDTO);
			}
			ChatRoomDTO flagTrueResult = messengerRepository.save(savedRoom);
			if (flagTrueResult != null) {
				result.put("check", true);
				result.put("result", flagTrueResult);
			}
		} else {
			Page<MessengerTestDTO> resultIfPresent = messengerRepository.chatList(pageable, roomNumIfPresent, 0L);
			for (MessengerTestDTO m : resultIfPresent.getContent()) {
				Optional<StaffDTO> staffDTO = staffRepository.findById(m.getStaffCode());
				m.setStaffName(staffDTO.get().getStaffName());
			}
			result.put("check", false);
			result.put("result", resultIfPresent);
			result.put("chatRoomNum", roomNumIfPresent);
		}

		return result;
	}

	public void unread(Long chatRoomNum) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer staffCode = staffDTO.get().getStaffCode();
		
		List<MessengerTestDTO> dto = messengerRepository.getLatest(chatRoomNum, staffCode);
		Long chatBodyNum = 0L;
		if (dto != null && dto.size() > 0) {			
			chatBodyNum = dto.getFirst().getChatBodyNum();
		}
		
		int result = messengerRepository.unread(chatRoomNum, staffCode, chatBodyNum);
	}

	public int getUnreadCounts(Long r) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer staffCode = staffDTO.get().getStaffCode();
		
		ChatUserDTO chatUserDTO = messengerRepository.getLatestChat(r, staffCode);
		Long chatBodyNum = chatUserDTO.getChatBodyNum();
		
		List<MessengerTestDTO> result = messengerRepository.unreadCounts(r, staffCode, chatBodyNum);
		return result.size();
	}

	public MessengerTestDTO getLatestMessage(Long r) {
		MessengerTestDTO result = messengerRepository.getTrueLatest(r);
		if (result == null || result.getChatBodyContent() == null || result.getChatBodyContent().equals("")) {
			result = new MessengerTestDTO();
			result.setChatBodyContent("메시지 없음");
		}
		return result;
	}

	public MessengerTestDTO newChat(MessengerTestDTO messengerTestDTO) {
		MessengerTestDTO result = messengerRepository.getTrueLatest(messengerTestDTO.getChatRoomNum());
		return result;
	}

	public List<ChatUserDTO> getNotify(ChatRoomDTO chatRoomDTO) {
		List<ChatUserDTO> result = messengerRepository.getNotify(chatRoomDTO.getChatRoomNum());
		return result;
	}

	public List<StaffDTO> getStaffForGroupChat(ChatRoomDTO chatRoomDTO) {
		List<ChatUserDTO> result = messengerRepository.getNotify(chatRoomDTO.getChatRoomNum());
		List<Integer> currentMemeber = new ArrayList<>();
		for (ChatUserDTO c : result) {
			currentMemeber.add(c.getStaffDTO().getStaffCode());
		}
		List<StaffDTO> resultForController = staffRepository.findByStaffCodeNotIn(currentMemeber);
		return resultForController;
	}

	public int joinMember(List<String> staffs, Long chatRoomNum) {
		MessengerTestDTO result = messengerRepository.getTrueLatest(chatRoomNum);
		Long chatBodyNum = result.getChatBodyNum();
		int temp = 0;
		for (String s : staffs) {
			Integer staffCode = Integer.parseInt(s);
			temp = messengerRepository.saveJoinStaffs(staffCode, chatRoomNum, chatBodyNum);
		}
		return temp;
	}

	public boolean leaveMember(ChatRoomDTO chatRoomDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer staffCode = staffDTO.get().getStaffCode();
		int result = messengerRepository.leaveMember(chatRoomDTO.getChatRoomNum(), staffCode);
		if (result > 0) return true;
		else return false;
	}

	public ChatRoomDTO findChatRoom(ChatRoomDTO chatRoomDTO) {
		ChatRoomDTO result = messengerRepository.findById(chatRoomDTO.getChatRoomNum()).get();
		if (result != null) return result;
		else return null;
	}

	
	
}

package com.goodee.finals.messenger;

import java.util.ArrayList;
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

	public List<StaffDTO> getStaff() {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaff = staffDTO.get().getStaffCode();
		List<StaffDTO> result = staffRepository.findByStaffCodeNot(loggedStaff);
		return result;
	}

	public ChatRoomDTO createRoom(List<Integer> addedStaff, ChatRoomDTO chatRoomDTO) {
		
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

	public List<ChatRoomDTO> list() {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		Integer loggedStaff = staffDTO.get().getStaffCode();
		List<ChatRoomDTO> result = messengerRepository.findChatRoomByStaffCode(loggedStaff);
		return result;
	}

	public MessengerTestDTO saveChat(MessengerTestDTO message) {
		MessengerTestDTO result = stompRepository.save(message);
		return result;
	}

	public Page<MessengerTestDTO> chatList(Pageable pageable, Long chatRoomNum) {
		Page<MessengerTestDTO> result = messengerRepository.chatList(pageable, chatRoomNum);
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
			Optional<StaffDTO> staffDTOSend = staffRepository.findById(sendStaffCode);
			chatRoomDTO.setChatRoomName(staffDTOSend.get().getStaffName() + " " + staffDTOLogged.get().getStaffName());
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
			Page<MessengerTestDTO> resultIfPresent = messengerRepository.chatList(pageable, roomNumIfPresent);
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
	
}

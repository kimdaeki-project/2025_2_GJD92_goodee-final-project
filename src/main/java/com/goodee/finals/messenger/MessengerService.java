package com.goodee.finals.messenger;

import java.util.ArrayList;
import java.util.List;
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
	
}

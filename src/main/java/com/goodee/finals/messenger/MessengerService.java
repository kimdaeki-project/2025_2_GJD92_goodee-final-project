package com.goodee.finals.messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}

package com.goodee.finals.attend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class AttendService {

	@Autowired
	AttendRepository attendRepository;
	
	@Autowired
	StaffRepository staffRepository;

	public AttendDTO attendIn(AttendDTO attendDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		attendDTO.setStaffDTO(staffDTO.get());
		AttendDTO result = attendRepository.save(attendDTO);
		return result;
	}
	
	public AttendDTO findAttend() {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		AttendDTO attendDTO = null;
		attendDTO.setStaffDTO(staffDTO.get());
		
		attendDTO = attendRepository.findByStaffCode(attendDTO.getStaffDTO().getStaffCode());
		return attendDTO;
	}
	
	
}

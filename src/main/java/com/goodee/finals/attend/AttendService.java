package com.goodee.finals.attend;

import java.time.LocalTime;
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
	
	public AttendDTO attendOut(AttendDTO attendDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		
//		Optional<AttendDTO> attendOptional = attendRepository.findByStaffDTOStaffCode(staffCode);
		attendDTO = attendRepository.findByStaffDTOStaffCode(staffCode).get();
		attendDTO.setAttendOut(LocalTime.now());
		
		AttendDTO result = attendRepository.save(attendDTO);
		return result;
		
	}
	
	public AttendDTO findAttend() {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		
		AttendDTO attendDTO = new AttendDTO();
		Optional<AttendDTO> attendOptional = attendRepository.findByStaffDTOStaffCode(staffCode);

		try {
			attendDTO = attendOptional.get();
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
		
		return attendDTO;
	}
	
}

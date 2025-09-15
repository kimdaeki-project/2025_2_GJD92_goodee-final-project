package com.goodee.finals.staff;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffService implements UserDetailsService {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public int registStaff(StaffDTO staffDTO) {
		staffDTO = setStaffDefault(staffDTO);
		StaffDTO result = staffRepository.saveAndFlush(staffDTO);
		
		return 0;
	}
	
	private StaffDTO setStaffDefault(StaffDTO staffDTO) {
		staffDTO.setDeptDTO(deptRepository.findById(staffDTO.getInputDeptCode()).orElseThrow());
		staffDTO.setJobDTO(jobRepository.findById(staffDTO.getInputJobCode()).orElseThrow());
		
		int year = LocalDate.now().getYear();
		Integer lastStaffCode = staffRepository.findLastStaffCode();
		
		if (lastStaffCode == null || (lastStaffCode / 10000) != year) {
			staffDTO.setStaffCode((year * 10000) + 1);
		} else {
			staffDTO.setStaffCode(lastStaffCode + 1);
		}
		
		staffDTO.setStaffPw(passwordEncoder.encode("0000"));
		staffDTO.setStaffUsedLeave(0);
		staffDTO.setStaffRemainLeave(0);
		
		return staffDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Integer staffCode;
		
		try {
			staffCode = Integer.parseInt(username);
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("올바른 사원번호를 입력해주세요.");
		}
		
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		return staffDTO;
	}

	

}

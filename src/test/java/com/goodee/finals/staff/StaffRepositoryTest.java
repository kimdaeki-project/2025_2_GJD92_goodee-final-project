package com.goodee.finals.staff;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class StaffRepositoryTest {
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void deptInsertTest() {
		DeptDTO deptDTO = new DeptDTO();
		deptDTO.setDeptCode(1000);
		deptDTO.setDeptName("인사");
		
		deptRepository.saveAndFlush(deptDTO);
	}
	
	@Test
	void staffInsertTest() {
		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setStaffCode(20250001);
		staffDTO.setStaffPw(passwordEncoder.encode("0000"));
		
		StaffDeptDTO staffDeptDTO = new StaffDeptDTO();
		DeptDTO deptDTO = deptRepository.findById(1000).get();
		staffDeptDTO.setDeptDTO(deptDTO);
		staffDeptDTO.setStaffDTO(staffDTO);
		staffDTO.setStaffDeptDTO(staffDeptDTO);
		
		StaffJobDTO staffJobDTO = new StaffJobDTO();
		JobDTO jobDTO = jobRepository.findById(1100).get();
		staffJobDTO.setJobDTO(jobDTO);
		staffJobDTO.setStaffDTO(staffDTO);
		staffDTO.setStaffJobDTO(staffJobDTO);
		
		staffRepository.saveAndFlush(staffDTO);
		
	}
	
	@Test
	void staffSelectTest() {
		StaffDTO staffDTO = staffRepository.findById(20250001).get();
		System.out.println(staffDTO.getStaffJobDTO().getJobDTO().getJobName());
	}

}

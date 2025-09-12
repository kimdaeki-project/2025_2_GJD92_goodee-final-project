package com.goodee.finals.staff;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class StaffRepositoryTest {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void staffInsertTest() {
		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setStaffCode(20250001);
		staffDTO.setStaffPw(passwordEncoder.encode("0000"));
		staffDTO.setDeptDTO(deptRepository.findById(1000).orElseThrow());
		staffDTO.setJobDTO(jobRepository.findById(1100).orElseThrow());
		
		staffRepository.saveAndFlush(staffDTO);
	}
	
	@Test
	void staffSelectTest() {
		StaffDTO staffDTO = staffRepository.findById(20250001).orElseThrow();
		System.out.println(staffDTO.getJobDTO().getJobName());
	}
	
	@Test
	void nativeQueryTest() {
		List<JobDTO> result = jobRepository.findAllJobs();
		result.forEach((job) -> {
			System.out.println(job.getJobName());
		});
	}

}

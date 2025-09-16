package com.goodee.finals.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class DriveService {

	@Autowired
	private DriveRepository driveRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	public List<StaffDTO> staffList() {
		return staffRepository.findAll();
	}
	
}

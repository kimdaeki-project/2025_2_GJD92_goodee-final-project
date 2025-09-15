package com.goodee.finals.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriveService {

	@Autowired
	private DriveRepository driveRepository;
	
}

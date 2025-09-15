package com.goodee.finals.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(name = "/drive/**")
@Slf4j
public class DriveController {

	@Autowired
	private DriveService driveService;
	
}

package com.goodee.finals.drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/drive/**")
@Slf4j
public class DriveController {

	@Autowired
	private DriveService driveService;
	
	@GetMapping("list")
	public String list() {
		return "drive/list";
	}
	
	@GetMapping("create")
	public String create(@ModelAttribute DriveDTO driveDTO) {
		return "drive/create";
	}
	
}

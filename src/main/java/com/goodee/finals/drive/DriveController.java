package com.goodee.finals.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.staff.StaffDTO;

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
	
	@GetMapping("staffList")
	@ResponseBody
	public List<StaffDTO> staffList() {
		
		List<StaffDTO> list = driveService.staffList();

		System.out.println("DriveController staffList 사이즈 : " + list.size());
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	//테스트중
	@GetMapping("test")
	public String test() { 
		return "drive/test/modal_test";
	}
	
}

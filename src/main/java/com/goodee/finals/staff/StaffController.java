package com.goodee.finals.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/staff/**")
@Slf4j
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	@GetMapping
	public String getStaffList() {
		return "staff/list";
	}

	@GetMapping("login")
	public String getStaffLogin() {
		return "staff/login";
	}
	
	@GetMapping("info")
	public String getStaffInfo() {
		return "staff/info";
	}
	
	@GetMapping("regist")
	public String getStaffRegist(@ModelAttribute StaffDTO staffDTO) {
		return "staff/form";
	}
	
	@PostMapping("regist")
	public String postStaffRegist(@Valid StaffDTO staffDTO, BindingResult bindingResult, MultipartFile attach) {
		int result = staffService.registStaff(staffDTO, attach);
		
		return "redirect:/";
	}
	
}

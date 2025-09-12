package com.goodee.finals.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff/**")
public class StaffController {

	@GetMapping("login")
	public String getStaffLogin() {
		return "staff/login";
	}
	
	@GetMapping("test")
	public String test() {
		return "common/template_sidebar";
	}
	
}

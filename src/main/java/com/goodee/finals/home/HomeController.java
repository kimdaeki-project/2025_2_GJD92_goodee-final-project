package com.goodee.finals.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.finals.attend.AttendDTO;
import com.goodee.finals.attend.AttendService;

@Controller
public class HomeController {

	@Autowired
	private AttendService attendService;
	
	@GetMapping("/")
	public String getHome(Model model) {
		if (SecurityContextHolder.getContext() != null) {
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		
		AttendDTO result = attendService.findAttend();
		
		return "index";
	}
	
}

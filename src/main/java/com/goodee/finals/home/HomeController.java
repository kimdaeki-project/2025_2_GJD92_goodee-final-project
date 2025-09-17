package com.goodee.finals.home;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String getHome() {
		if (SecurityContextHolder.getContext() != null) {
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		// test
		
		return "index";
	}
	
}

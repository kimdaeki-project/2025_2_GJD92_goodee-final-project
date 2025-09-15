package com.goodee.finals.attend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attend/**")
public class AttendController {

	@GetMapping("in")
	public void attendIn() {
		System.out.println("출근함");
	}
	
	@GetMapping("out")
	public void attendOut() {
		System.out.println("퇴근함");
	}
	
}

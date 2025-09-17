package com.goodee.finals.attend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/attend/**")
@Slf4j
public class AttendController {

	@Autowired
	private AttendService attendService;
	
	@PostMapping("in")
	public String attendIn(AttendDTO attendDTO, Model model) {
		AttendDTO result = attendService.attendIn(attendDTO);
		
		if (result != null) {
			model.addAttribute("attendDTO", result);
			log.info("출근체크");
			return "redirect:/";
		} else {
			return null;
		}
		
	}
	
	@PostMapping("out")
	public String attendOut(AttendDTO attendDTO, Model model) {
		AttendDTO result = attendService.attendOut(attendDTO);
		
		if (result != null) {
			model.addAttribute("attendDTO", result);
			log.info("퇴근체크");
			return "redirect:/";
		} else {
			return null;
		}
	}
	
}

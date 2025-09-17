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
		
		String resultMsg = "출근처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "출근처리가 완료되었습니다.";
			resultIcon = "success";
			String resultUrl = "/";
			model.addAttribute("attendDTO", result);
			log.info("출근체크");
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			return null;
		}
		
	}
	
	@PostMapping("out")
	public String attendOut(AttendDTO attendDTO, Model model) {
		AttendDTO result = attendService.attendOut(attendDTO);
		
		String resultMsg = "퇴근처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "퇴근처리가 완료되었습니다.";
			resultIcon = "success";
			String resultUrl = "/";
			model.addAttribute("attendDTO", result);
			log.info("퇴근체크");
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			return null;
		}
	}
	
}

package com.goodee.finals.home;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.finals.attend.AttendDTO;
import com.goodee.finals.attend.AttendService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@Autowired
	private AttendService attendService;
	
	@GetMapping("/")
	public String getHome(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 안 된 경우
        if (auth == null || auth.getName().equals("anonymousUser")) {
            return "redirect:/staff/login";
        }
		
		AttendDTO attendDTO = attendService.findAttend();
		log.info("{}", attendDTO);
		model.addAttribute("attendDTO", attendDTO);
		
		// ✅ 오늘 날짜 추가
	    String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    model.addAttribute("todayDate", todayDate);
	    
		return "index";
	}
	
}

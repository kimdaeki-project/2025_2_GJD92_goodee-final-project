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
	
	// 대시보드
	@PostMapping("in")
	public String attendIn(AttendDTO attendDTO, Model model) {
		
		String resultMsg = "출근처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		String resultUrl = "/";

		try {
			AttendDTO result = attendService.attendIn(attendDTO);
			model.addAttribute("attendDTO", result);
			
			resultMsg = "출근처리가 완료되었습니다.";
			resultIcon = "success";
			log.info("출근체크");
			
		} catch (IllegalStateException e) {
			// TODO: handle exception
			resultMsg = "오늘은 이미 출근 기록이 있습니다.";
			resultIcon = "warning";
			log.warn("출근체크 실패: {}", e.getMessage());
		} catch (Exception e) {
            resultMsg = "출근처리 중 오류가 발생했습니다.";
            resultIcon = "error";
            log.error("출근체크 에러", e);
        }
			
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		model.addAttribute("resultUrl", resultUrl);
			
		return "common/result";
		
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
			model.addAttribute("resultUrl", resultUrl);
			log.info("퇴근체크");
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			return null;
		}
	}
	
	// 근태 페이지
	@GetMapping("")
	public String list() {
		return "attend/list";
	}
	
}

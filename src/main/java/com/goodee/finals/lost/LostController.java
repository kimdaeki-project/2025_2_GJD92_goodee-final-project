package com.goodee.finals.lost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/lost/**")
@Slf4j
public class LostController {

	@Autowired
	private LostService lostService;
	
	@GetMapping("write")
	public String write() throws Exception {
		return "lost/write";
	}
	
	@PostMapping("write")
	public String write(LostDTO lostDTO, MultipartFile attach, Model model) throws Exception {
		boolean result = lostService.write(lostDTO, attach);
		
		String resultMsg = "품목 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (!result) {
			resultMsg = "품목을 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/"; // 수정하기
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	
}

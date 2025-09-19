package com.goodee.finals.approval;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/approval/**")
public class ApprovalController {

	@GetMapping
	public String getApprovalList() {
		return "approval/list";
	}
	
	@GetMapping("draft")
	public String getApprovalDraft(Model model) {
		LocalDate nowDate = LocalDate.now();
		model.addAttribute("nowDate", nowDate);
		
		return "approval/draft";
	}
	
}

package com.goodee.finals.approval;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/approval/**")
@Slf4j
public class ApprovalController {
	@Autowired
	private StaffService staffService;
	@Autowired
	private ApprovalService approvalService;

	@GetMapping
	public String getApprovalList() {
		return "approval/list";
	}
	
	@GetMapping("staff")
	@ResponseBody
	public List<StaffDTO> getApprovalStaffList() {
		log.info("요청 들어옴");
		return staffService.getStaffAll();
	}
	
	@GetMapping("draft")
	public String getApprovalDraft(Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		
		return "approval/draft";
	}
	
	@PostMapping("draft")
	public String postApprovalDraft(InputApprovalDTO inputApprovalDTO, MultipartFile attach, Model model) {
		log.info("{}", attach.getSize());
		
		return null;
	}
	
}

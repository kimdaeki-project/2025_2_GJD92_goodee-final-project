package com.goodee.finals.approval;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String getApprovalList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalListDTO> approvalList = approvalService.getApprovalList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/list";
	}
	
	@GetMapping("request")
	public String getApprovalRequestList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalListDTO> approvalList = approvalService.getApprovalRequestList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/list";
	}
	
	@GetMapping("ready")
	public String getApprovalReadyList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalListDTO> approvalList = approvalService.getApprovalReadyList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/list";
	}
	
	@GetMapping("mine")
	public String getApprovalMineList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalMineList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/list";
	}
	
	@GetMapping("finish")
	public String getApprovalFinishList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalFinishList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/list";
	}
	
	@GetMapping("{aprvCode}")
	public String getApprovalDetail(@PathVariable Integer aprvCode, Model model) {
		ApprovalDTO approvalDTO = approvalService.getApprovalDetail(aprvCode);
		model.addAttribute("approval", approvalDTO);
		
		List<DeptDTO> deptList = approvalService.getDeptList();
		model.addAttribute("deptList", deptList);
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("isMyTurn", "N");
		if (approvalDTO.getApproverDTOs() != null && approvalDTO.getAprvState() == 701) {
			for (ApproverDTO approver : approvalDTO.getApproverDTOs()) {
				if (approver.getStaffDTO().getStaffCode().equals(staffDTO.getStaffCode()) && approver.getApvrState() == 721) {
					model.addAttribute("isMyTurn", "Y");
				}
			}			
		}
		
		return "approval/detail";
	}
	
	@PostMapping("{aprvCode}/check")
	public String postApprovalCheck(@PathVariable Integer aprvCode, String apvrResult, String apvrComment, Model model) {
		boolean result = false;
		
		if (apvrResult.equals("true")) {
			result = approvalService.checkApprovalTrue(aprvCode, apvrComment);
		} else {
			result = approvalService.checkApprovalFalse(aprvCode, apvrComment);
		}
		
		String resultMsg = "결재 처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "결재가 처리되었습니다.";
			resultIcon = "success";
			String resultUrl = "/approval/" + aprvCode;
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("staff")
	@ResponseBody
	public List<StaffDTO> getApprovalStaffList() {
		return staffService.getStaffAll();
	}
	
	@GetMapping("draft")
	public String getApprovalDraft(Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		int year = LocalDate.now().getYear();
		Integer lastStaffCode = approvalService.findLastAprvCode();
		
		Integer aprvCode = null;
		if (lastStaffCode == null || (lastStaffCode / 1000000) != year) {
			aprvCode = (year * 1000000) + 1;
		} else {
			aprvCode = lastStaffCode + 1;
		}
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		model.addAttribute("aprvCode", aprvCode);
		model.addAttribute("draftForm", "common");
		
		return "approval/draft";
	}
	
	@PostMapping("draft")
	public String postApprovalDraft(InputApprovalDTO inputApprovalDTO, MultipartFile[] attach, Model model) throws IOException {
		boolean result = approvalService.sendNormalDraft(inputApprovalDTO, attach);
		
		String resultMsg = "기안 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "기안을 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/approval";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("recept")
	public String getApprovalRecept(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalRecept(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/receive";
	}
}

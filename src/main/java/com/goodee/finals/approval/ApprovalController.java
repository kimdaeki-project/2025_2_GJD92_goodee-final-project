package com.goodee.finals.approval;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffService;

import jakarta.servlet.http.HttpServletRequest;
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
		
		setApprovalData(staffDTO.getStaffCode(), model);
		return "approval/list";
	}
	
	@GetMapping("request")
	public String getApprovalRequestList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalListDTO> approvalList = approvalService.getApprovalRequestList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		setApprovalData(staffDTO.getStaffCode(), model);
		return "approval/list";
	}
	
	@GetMapping("ready")
	public String getApprovalReadyList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalListDTO> approvalList = approvalService.getApprovalReadyList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		setApprovalData(staffDTO.getStaffCode(), model);
		return "approval/list";
	}
	
	@GetMapping("mine")
	public String getApprovalMineList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalMineList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		setApprovalData(staffDTO.getStaffCode(), model);
		return "approval/list";
	}
	
	@GetMapping("finish")
	public String getApprovalFinishList(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalFinishList(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		setApprovalData(staffDTO.getStaffCode(), model);
		return "approval/list";
	}
	
	@GetMapping("load")
	public String getApprovalLoad(Integer savedCode) {
		ApprovalDTO approvalDTO = approvalService.getApprovalDetail(savedCode);
		
		if (approvalDTO.getAprvType() == 901) {
			return "redirect:/approval/draft/vacation?aprvCode=" + savedCode;
		} else if (approvalDTO.getAprvType() == 902) {
			return "redirect:/approval/draft/overtime?aprvCode=" + savedCode;
		} else if (approvalDTO.getAprvType() == 903) {
			return "redirect:/approval/draft/early?aprvCode=" + savedCode;
		} else {
			return "redirect:/approval/draft?aprvCode=" + savedCode;			
		}
	}
	
	@GetMapping("delete")
	public String getApprovalDelete(Integer savedCode, Model model) {
		boolean result = approvalService.deleteApproval(savedCode);
		
		String resultMsg = "기안 삭제 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "임시저장한 기안이 삭제되었습니다.";
			resultIcon = "success";
			String resultUrl = "/approval/draft";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{aprvCode}")
	public String getApprovalDetail(@PathVariable Integer aprvCode, Model model) {
		ApprovalDTO approvalDTO = approvalService.getApprovalDetail(aprvCode);
		model.addAttribute("approval", approvalDTO);
		
		List<DeptDTO> deptList = approvalService.getDeptList();
		model.addAttribute("deptList", deptList);
		
		if (approvalDTO.getOvertimeDTO() != null) {
			model.addAttribute("overtimeStart", approvalDTO.getOvertimeDTO().getOverStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			model.addAttribute("overtimeEnd", approvalDTO.getOvertimeDTO().getOverEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		}
		
		if (approvalDTO.getEarlyDTO() != null) {
			model.addAttribute("earlyTime", approvalDTO.getEarlyDTO().getEarlyDtm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		}
		
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
	public String getApprovalDraft(Integer aprvCode, Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		
		if (aprvCode != null) {
			ApprovalDTO approval = approvalService.getApprovalDetail(aprvCode);
			model.addAttribute("approval", approval);
			
			for (ApproverDTO approver : approval.getApproverDTOs()) {
				if (approver.getApvrType() == 711 || approver.getApvrType() == 712) {
					model.addAttribute("hasApprover", true);
					break;
				}
			}
		} else {
			int year = LocalDate.now().getYear();
			Integer lastStaffCode = approvalService.findLastAprvCode();
			
			if (lastStaffCode == null || (lastStaffCode / 1000000) != year) {
				aprvCode = (year * 1000000) + 1;
			} else {
				aprvCode = lastStaffCode + 1;
			}
		}
		
		model.addAttribute("aprvCode", aprvCode);			
		model.addAttribute("draftForm", "common");
		return "approval/draft";
	}
	
	@PostMapping({"draft", "draft/save"})
	public String postApprovalDraft(InputApprovalDTO inputApprovalDTO, MultipartFile[] attach, HttpServletRequest request, Model model) throws IOException {
		if (request.getRequestURI().endsWith("/save")) {
			boolean result = approvalService.sendNormalDraft(inputApprovalDTO, attach, true);
			
			String resultMsg = "기안 임시저장 중 오류가 발생했습니다.";
			String resultIcon = "warning";
			
			if (result) {
				resultMsg = "기안을 임시저장 했습니다.";
				resultIcon = "success";
				String resultUrl = "/approval";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			String resultMsg = "기안 등록 중 오류가 발생했습니다.";
			String resultIcon = "warning";
			
			if (inputApprovalDTO.getApprover().size() == 0) {
				resultMsg = "결재선이 지정되지 않았습니다.";
				
				model.addAttribute("resultMsg", resultMsg);
				model.addAttribute("resultIcon", resultIcon);
				
				return "common/result";
			} 
			
			boolean result = approvalService.sendNormalDraft(inputApprovalDTO, attach, false);
			
			if (result) {
				resultMsg = "기안을 등록했습니다.";
				resultIcon = "success";
				String resultUrl = "/approval";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			// 알림
			List<String> wsSub = new ArrayList<>();
			for (String sub : inputApprovalDTO.getApprover()) wsSub.add(sub);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				model.addAttribute("wsSub", objectMapper.writeValueAsString(wsSub));
				model.addAttribute("wsMsg", "내 앞으로 새로운 결재가 등록되었습니다.," + inputApprovalDTO.getAprvCode());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return "common/notifyResult";
		}
		
	}
	
	@GetMapping("draft/vacation")
	public String getApprovalDraftVacation(Integer aprvCode, Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		
		if (aprvCode != null) {
			ApprovalDTO approval = approvalService.getApprovalDetail(aprvCode);
			model.addAttribute("approval", approval);
			
			for (ApproverDTO approver : approval.getApproverDTOs()) {
				if (approver.getApvrType() == 711 || approver.getApvrType() == 712) {
					model.addAttribute("hasApprover", true);
					break;
				}
			}
		} else {
			int year = LocalDate.now().getYear();
			Integer lastStaffCode = approvalService.findLastAprvCode();
			
			if (lastStaffCode == null || (lastStaffCode / 1000000) != year) {
				aprvCode = (year * 1000000) + 1;
			} else {
				aprvCode = lastStaffCode + 1;
			}
		}
		
		model.addAttribute("aprvCode", aprvCode);
		model.addAttribute("draftForm", "vacation");
		return "approval/draft";
	}
	
	@PostMapping({"draft/vacation", "draft/vacation/save"})
	public String postApprovalDraftVacation(InputApprovalDTO inputApprovalDTO, VacationDTO vacationDTO, HttpServletRequest request, Model model) {
		if (request.getRequestURI().endsWith("/save")) {
			boolean result = approvalService.sendVacationDraft(inputApprovalDTO, vacationDTO, true);
			
			String resultMsg = "기안 임시저장 중 오류가 발생했습니다.";
			String resultIcon = "warning";
			
			if (result) {
				resultMsg = "기안을 임시저장 했습니다.";
				resultIcon = "success";
				String resultUrl = "/approval";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			boolean result = approvalService.sendVacationDraft(inputApprovalDTO, vacationDTO, false);
			
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
			
			// 알림
			List<String> wsSub = new ArrayList<>();
			for (String sub : inputApprovalDTO.getApprover()) wsSub.add(sub);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				model.addAttribute("wsSub", objectMapper.writeValueAsString(wsSub));
				model.addAttribute("wsMsg", "내 앞으로 새로운 결재가 등록되었습니다.," + inputApprovalDTO.getAprvCode());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return "common/notifyResult";
		}
	}
	
	@GetMapping("draft/overtime")
	public String getApprovalDraftOvertime(Integer aprvCode, Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		
		if (aprvCode != null) {
			ApprovalDTO approval = approvalService.getApprovalDetail(aprvCode);
			model.addAttribute("approval", approval);
			
			for (ApproverDTO approver : approval.getApproverDTOs()) {
				if (approver.getApvrType() == 711 || approver.getApvrType() == 712) {
					model.addAttribute("hasApprover", true);
					break;
				}
			}
		} else {
			int year = LocalDate.now().getYear();
			Integer lastStaffCode = approvalService.findLastAprvCode();
			
			if (lastStaffCode == null || (lastStaffCode / 1000000) != year) {
				aprvCode = (year * 1000000) + 1;
			} else {
				aprvCode = lastStaffCode + 1;
			}
		}
		
		model.addAttribute("aprvCode", aprvCode);
		model.addAttribute("draftForm", "overtime");
		return "approval/draft";
	}
	
	@PostMapping({"draft/overtime", "draft/overtime/save"})
	public String postApprovalDraftOvertime(InputApprovalDTO inputApprovalDTO, OvertimeDTO overtimeDTO, HttpServletRequest request, Model model) {
		if (request.getRequestURI().endsWith("/save")) {
			boolean result = approvalService.sendOvertimeDraft(inputApprovalDTO, overtimeDTO, true);
			
			String resultMsg = "기안 임시저장 중 오류가 발생했습니다.";
			String resultIcon = "warning";
			
			if (result) {
				resultMsg = "기안을 임시저장 했습니다.";
				resultIcon = "success";
				String resultUrl = "/approval";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			boolean result = approvalService.sendOvertimeDraft(inputApprovalDTO, overtimeDTO, false);
			
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
			
			// 알림
			List<String> wsSub = new ArrayList<>();
			for (String sub : inputApprovalDTO.getApprover()) wsSub.add(sub);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				model.addAttribute("wsSub", objectMapper.writeValueAsString(wsSub));
				model.addAttribute("wsMsg", "내 앞으로 새로운 결재가 등록되었습니다.," + inputApprovalDTO.getAprvCode());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return "common/notifyResult";
		}
	}
	
	@GetMapping("draft/early")
	public String getApprovalDraftEarly(Integer aprvCode, Model model) {
		LocalDate nowDate = LocalDate.now();
		List<DeptDTO> deptList = approvalService.getDeptList();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("deptList", deptList);
		
		if (aprvCode != null) {
			ApprovalDTO approval = approvalService.getApprovalDetail(aprvCode);
			model.addAttribute("approval", approval);
			
			for (ApproverDTO approver : approval.getApproverDTOs()) {
				if (approver.getApvrType() == 711 || approver.getApvrType() == 712) {
					model.addAttribute("hasApprover", true);
					break;
				}
			}
		} else {
			int year = LocalDate.now().getYear();
			Integer lastStaffCode = approvalService.findLastAprvCode();
			
			if (lastStaffCode == null || (lastStaffCode / 1000000) != year) {
				aprvCode = (year * 1000000) + 1;
			} else {
				aprvCode = lastStaffCode + 1;
			}
		}
		
		model.addAttribute("aprvCode", aprvCode);
		model.addAttribute("draftForm", "early");
		return "approval/draft";
	}
	
	@PostMapping({"draft/early", "draft/early/save"})
	public String postApprovalDraftEarly(InputApprovalDTO inputApprovalDTO, EarlyDTO earlyDTO, HttpServletRequest request, Model model) {
		if (request.getRequestURI().endsWith("/save")) {
			boolean result = approvalService.sendEarlyDraft(inputApprovalDTO, earlyDTO, true);
			
			String resultMsg = "기안 임시저장 중 오류가 발생했습니다.";
			String resultIcon = "warning";
			
			if (result) {
				resultMsg = "기안을 임시저장 했습니다.";
				resultIcon = "success";
				String resultUrl = "/approval";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			boolean result = approvalService.sendEarlyDraft(inputApprovalDTO, earlyDTO, false);
			
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
			
			// 알림
			List<String> wsSub = new ArrayList<>();
			for (String sub : inputApprovalDTO.getApprover()) wsSub.add(sub);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				model.addAttribute("wsSub", objectMapper.writeValueAsString(wsSub));
				model.addAttribute("wsMsg", "내 앞으로 새로운 결재가 등록되었습니다.," + inputApprovalDTO.getAprvCode());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return "common/notifyResult";
		}
	}
	
	@GetMapping("recept")
	public String getApprovalRecept(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Page<ApprovalResultDTO> approvalList = approvalService.getApprovalRecept(staffDTO.getStaffCode(), search, pageable);
		model.addAttribute("approvalList", approvalList);
		
		return "approval/receive";
	}
	
	@GetMapping("sign")
	public String getApprovalSign() {
		return "approval/sign";
	}
	
	@PostMapping("sign")
	@ResponseBody
	public boolean postApprovalSign(MultipartFile attach) throws IOException {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		boolean result = approvalService.setApprovalSign(staffDTO, attach);
		
		if (result) {
			staffDTO = staffService.getStaff(staffDTO.getStaffCode());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(staffDTO, authentication.getCredentials(), authentication.getAuthorities()));
		}
		
		return result;
	}
	
	@PostMapping("line")
	public String postApprovalLineChange(InputApprovalDTO inputApprovalDTO, Model model) {
		log.info("{}", inputApprovalDTO);
		boolean result = approvalService.changeApprovalLine(inputApprovalDTO);
		
		String resultMsg = "결재선 재지정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "결재선을 재지정했습니다.";
			resultIcon = "success";
			String resultUrl = "/approval";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{attachNum}/download")
	public String getFileDownload(@PathVariable String attachNum, Model model) {
		AttachmentDTO result = approvalService.getAttach(Long.valueOf(attachNum));
		model.addAttribute("file", result);
		model.addAttribute("type", "approval");
		
		return "fileDownView";
	}
	
	@GetMapping("{attachNum}/delete")
	@ResponseBody
	public boolean getFileDelete(@PathVariable String attachNum) {
		return approvalService.deleteAttach(attachNum);
	}
	
	@PostMapping("{apvrCode}/receive")
	@ResponseBody
	public boolean postApprovalAddReceiver(@PathVariable String apvrCode, String[] receiver) {
		return approvalService.addReceiver(apvrCode, receiver);
	}
	
	@GetMapping("save/list")
	@ResponseBody
	public List<ApprovalResultDTO> getApprovalSaved() {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return approvalService.getApprovalSaved(staffDTO.getStaffCode());
	}
	
	private void setApprovalData(Integer staffCode, Model model) {
		Integer request = approvalService.getApprovalRequestCount(staffCode);
		Integer ready = approvalService.getApprovalReadyCount(staffCode);
		Integer mine = approvalService.getApprovalMineCount(staffCode);
		Integer finish = approvalService.getApprovalFinishCount(staffCode);
		
		model.addAttribute("totalRequest", request);
		model.addAttribute("totalReady", ready);
		model.addAttribute("totalMine", mine);
		model.addAttribute("totalFinish", finish);
	}
}

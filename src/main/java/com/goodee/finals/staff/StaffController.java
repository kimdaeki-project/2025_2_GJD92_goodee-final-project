package com.goodee.finals.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/staff/**")
@Slf4j
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	@GetMapping
	public String getStaffList(@PageableDefault(size = 10, sort = "staff_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffDTO> staffList = staffService.getStaffSearchList(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("search", search);
		
		long totalStaff = staffService.getTotalStaff();
		model.addAttribute("totalStaff", totalStaff);
		
		return "staff/list";
	}
	
	@GetMapping("{staffCode}")
	public String getStaffDetail(@PathVariable Integer staffCode, Model model) {
		StaffDTO staffDTO = staffService.getStaff(staffCode);
		model.addAttribute("staff", staffDTO);
		
		return "staff/detail";
	}
	
	@GetMapping("{staffCode}/update")
	public String getStaffUpdate(@PathVariable Integer staffCode, Model model) {
		StaffDTO staffDTO = staffService.getStaff(staffCode);
		staffDTO.setInputDeptCode(staffDTO.getDeptDTO().getDeptCode());
		staffDTO.setInputJobCode(staffDTO.getJobDTO().getJobCode());
		
		model.addAttribute("staffDTO", staffDTO);
		
		return "staff/form";
	}
	
	@PostMapping("{staffCode}/update")
	public String postStaffUpdate(@Valid StaffDTO staffDTO, BindingResult bindingResult, MultipartFile attach, Model model) {
		log.info("{}", staffDTO.getStaffCode());
		
		boolean result = staffService.updateStaff(staffDTO, attach);
		
		String resultMsg = "사원 정보 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "사원 정보를 수정했습니다.";
			resultIcon = "success";
			String resultUrl = "/staff/" + staffDTO.getStaffCode();
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}

	@GetMapping("login")
	public String getStaffLogin() {
		return "staff/login";
	}
	
	@GetMapping("info")
	public String getStaffInfo() {
		return "staff/info";
	}
	
	@GetMapping("regist")
	public String getStaffRegist(@ModelAttribute StaffDTO staffDTO) {
		return "staff/form";
	}
	
	@PostMapping("regist")
	public String postStaffRegist(@Valid StaffDTO staffDTO, BindingResult bindingResult, MultipartFile attach, Model model) {
		boolean result = staffService.registStaff(staffDTO, attach);
		
		String resultMsg = "사원 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "사원을 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/staff";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{staffCode}/unlock")
	public String getStaffUnlock(@PathVariable Integer staffCode, Model model) {
		boolean result = staffService.unlockStaff(staffCode);
		
		String resultMsg = "이미 차단 해제된 계정입니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "계정 차단이 해제되었습니다.";
			resultIcon = "success";
			String resultUrl = "/staff/" + staffCode;
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@PostMapping("password/update")
	public String postStaffPasswordUpdate(PasswordDTO passwordDTO, Model model) {
		boolean result = staffService.updateStaffPassword(passwordDTO);
		
		String resultMsg = "비밀번호가 일치하지 않습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "비밀번호가 변경되었습니다. 다시 로그인해주세요.";
			resultIcon = "success";
			String resultUrl = "/staff/logout";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
}

package com.goodee.finals.staff;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
		List<Integer> leavedStaffList = staffService.getLeavedStaffToday();
		for (StaffDTO staffDTO : staffList) {
			if (leavedStaffList.contains(staffDTO.getStaffCode())) {
				staffDTO.setTodayState("연차");
				continue;
			}
			
			if (!CollectionUtils.isEmpty(staffDTO.getAttendDTOs()) && !ObjectUtils.isEmpty(staffDTO.getAttendDTOs().getFirst().getAttendIn())) {
				if (!ObjectUtils.isEmpty(staffDTO.getAttendDTOs().getFirst().getAttendOut())) {
					staffDTO.setTodayState("퇴근");
				} else {
					staffDTO.setTodayState("근무중");
				}
			} else {
				LocalTime nowTime = LocalTime.now();
				LocalTime deadline = LocalTime.of(18, 0);
				
				if (nowTime.isBefore(deadline)) {
					staffDTO.setTodayState("미출근");
				} else {
					staffDTO.setTodayState("결근");
				}
			}
		}

		model.addAttribute("staffList", staffList);
		model.addAttribute("search", search);
		
		long totalStaff = staffService.getTotalStaff();
		model.addAttribute("totalStaff", totalStaff);
		
		long leavedStaff = leavedStaffList.size();
		model.addAttribute("leavedStaff", leavedStaff);
		
		long workingStaff = staffService.getWorkingStaffToday();
		model.addAttribute("workingStaff", workingStaff);
		
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
	
	@GetMapping("leave")
	public String getStaffLeave(@PageableDefault(size = 10, sort = "staff_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffDTO> staffList = staffService.getStaffSearchList(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("search", search);
		
		Integer remainLeave = staffService.getStaffLeaveTotal();
		Integer usedLeave = staffService.getStaffLeaveUsed();
		Integer ownLeave = remainLeave - usedLeave;
		Double leaveRate = (double) Math.round(((double) usedLeave / remainLeave) * 1000) / 10;
		
		model.addAttribute("remainLeave", remainLeave);
		model.addAttribute("usedLeave", usedLeave);
		model.addAttribute("ownLeave", ownLeave);
		model.addAttribute("leaveRate", leaveRate);
		
		return "staff/list-leave";
	}
	
	@PostMapping("leave/update")
	public String postStaffLeaveUpdate(LeaveDTO leaveDTO, Model model) {
		boolean result = staffService.updateStaffLeave(leaveDTO);
		
		String resultMsg = "연차 정보 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "연차 정보를 수정했습니다.";
			resultIcon = "success";
			String resultUrl = "/staff/leave";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("quit")
	public String getStaffQuit(@PageableDefault(size = 10, sort = "staff_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffDTO> staffList = staffService.getStaffQuitSearchList(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("search", search);
		
		return "staff/list-quit";
	}

	@GetMapping("login")
	public String getStaffLogin() {
		return "staff/login";
	}
	
	@GetMapping("info")
	public String getStaffInfo() {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		staffDTO = staffService.getStaff(staffDTO.getStaffCode());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(staffDTO, authentication.getCredentials(), authentication.getAuthorities()));
		
		return "staff/info";
	}
	
	@GetMapping("info/update")
	public String getStaffInfoUpdate(Model model) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		staffDTO = staffService.getStaff(staffDTO.getStaffCode());
		model.addAttribute("staffDTO", staffDTO);
		
		return "staff/info-update";
	}
	
	@PostMapping("info/update")
	public String postStaffInfoUpdate(@Valid StaffDTO staffDTO, BindingResult bindingResult, MultipartFile attach, Model model) {
		boolean result = staffService.updateStaffFromInfo(staffDTO, attach);
		
		String resultMsg = "내 정보 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "내 정보를 수정했습니다.";
			resultIcon = "success";
			String resultUrl = "/staff/info";
			model.addAttribute("resultUrl", resultUrl);
			
			staffDTO = staffService.getStaff(staffDTO.getStaffCode());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(staffDTO, authentication.getCredentials(), authentication.getAuthorities()));
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
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
	
	@GetMapping("{staffCode}/disable")
	public String getStaffDisable(@PathVariable Integer staffCode, Model model) {
		boolean result = staffService.disableStaff(staffCode);
		
		String resultMsg = "이미 비활성화된 계정입니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "계정이 비활성화 되었습니다.";
			resultIcon = "success";
			String resultUrl = "/staff/" + staffCode;
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{staffCode}/enable")
	public String getStaffEnable(@PathVariable Integer staffCode, Model model) {
		boolean result = staffService.enableStaff(staffCode);
		
		String resultMsg = "이미 활성화된 계정입니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "계정이 활성화 되었습니다.";
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
		int result = staffService.updateStaffPassword(passwordDTO);
		
		String resultMsg = "비밀번호 변경 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result == 200) {
			resultMsg = "비밀번호가 변경되었습니다. 다시 로그인해주세요.";
			resultIcon = "success";
			String resultUrl = "/staff/logout";
			model.addAttribute("resultUrl", resultUrl);
		} else if (result == 400) {
			resultMsg = "기본 비밀번호는 사용할 수 없습니다.";
		} else if (result == 401) {
			resultMsg = "현재 비밀번호가 일치하지 않습니다.";
		} else if (result == 402) {
			resultMsg = "비밀번호 확인이 일치하지 않습니다.";
		} else if (result == 403) {
			resultMsg = "현재 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.";
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("password/change")
	public String getStaffPasswordChange() {
		return "staff/default-pw";
	}
	
	@GetMapping("vacation")
	public String getStaffVacation(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffVacationDTO> staffList = staffService.getStaffVacation(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("searchCode", "vacation");
		model.addAttribute("searchType", "휴가 사용 내역");
		
		return "staff/list-search";
	}
	
	@GetMapping("overtime")
	public String getStaffOvertime(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffOvertimeDTO> staffList = staffService.getStaffOvertime(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("searchCode", "overtime");
		model.addAttribute("searchType", "연장근무 내역");
		
		return "staff/list-search";
	}
	
	@GetMapping("early")
	public String getStaffEarly(@PageableDefault(size = 10) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<StaffEarlyDTO> staffList = staffService.getStaffEarly(search, pageable);
		model.addAttribute("staffList", staffList);
		model.addAttribute("searchCode", "early");
		model.addAttribute("searchType", "조기 퇴근 내역");
		
		return "staff/list-search";
	}
}

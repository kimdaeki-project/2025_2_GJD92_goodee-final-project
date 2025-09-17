package com.goodee.finals.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/drive/**")
@Slf4j
public class DriveController {

	@Autowired
	private DriveService driveService;
	
	@GetMapping
	public String list() {
		return "drive/list";
	}
	
	@GetMapping("create")
	public String create(@ModelAttribute DriveDTO driveDTO) {
		return "drive/create";
	}
	
	@PostMapping("create")
	public String createDrive(DriveDTO driveDTO, Model model) {
		StaffDTO staffDTO = null;
		// 로그인 안됐을경우 - 나중에 삭제
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(obj instanceof StaffDTO) {
			staffDTO = (StaffDTO) obj;
		} else {
			model.addAttribute("resultMsg", "로그인안됨");
			model.addAttribute("resultIcon", "error");
			model.addAttribute("resultUrl", "/drive");
			return "common/result";
		}
		driveDTO.setStaffDTO(staffDTO);
		driveService.createDrive(driveDTO);
		// TODO 드라이브 이름 유효성 검사 필요
		
		// 생성완료시 /drive 리다이렉트
		return "";
	}
	
	@GetMapping("staffList")
	@ResponseBody
	public List<StaffDTO> staffList() {
		List<StaffDTO> list = driveService.staffList();
		System.out.println("DriveController staffList 사이즈 : " + list.size());
		return list;
	}
	
}

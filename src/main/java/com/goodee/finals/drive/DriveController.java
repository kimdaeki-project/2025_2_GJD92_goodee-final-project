package com.goodee.finals.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffResponseDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/drive/**")
@Slf4j
public class DriveController {

	@Autowired
	private DriveService driveService;
	
	@GetMapping("staffList")
	@ResponseBody
	public List<StaffResponseDTO> staffList() {
		List<StaffResponseDTO> list = driveService.staffList();
		return list;
	}

    @ModelAttribute
    public void sideBarDriveList(Model model) {
    	StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriveDTO> myDriveList = driveService.myDrive(staffDTO);
		List<DriveShareDTO> shareDriveList = driveService.shareDrive(staffDTO);
		model.addAttribute("myDriveList", myDriveList);
		model.addAttribute("shareDriveList", shareDriveList);
		model.addAttribute("staffDTO", staffDTO);
    }
    
	@GetMapping
	public String getDefaultDrive(Model model) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriveDTO driveDTO = driveService.getDefaultDrive(staffDTO);
		model.addAttribute("driveDTO", driveDTO);
		return "drive/detail";
	}
	
	@GetMapping("{driveNum}")
	public String selectedDrive(@PathVariable Long driveNum, Model model) {
		DriveDTO driveDTO = driveService.getDrive(driveNum);
		model.addAttribute("driveDTO", driveDTO);
		return "drive/detail";
	}
	
	@GetMapping("create")
	public String create(@ModelAttribute DriveDTO driveDTO, Model model) {
		List<DeptDTO> deptList = driveService.deptList();
		model.addAttribute("deptList", deptList);
		return "drive/create";
	}
	
	@PostMapping("create")
	public String createDrive(@Valid DriveDTO driveDTO,BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
	        return "drive/create";
	    }
		StaffDTO staffDTO = (StaffDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		driveDTO.setStaffDTO(staffDTO);
		driveDTO = driveService.createDrive(driveDTO);
		
		if(driveDTO != null) {
			model.addAttribute("resultMsg", "드라이브 등록 완료");
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/drive");
		} else {
			model.addAttribute("resultMsg", "드라이드 등록 실패");
			model.addAttribute("resultIcon", "error");
			model.addAttribute("resultUrl", "/drive/create");
		}
		return "common/result";
	}
	
	@GetMapping("{driveNum}/update")
	public String updateDrive(@PathVariable Long driveNum, Model model) {
		DriveDTO driveDTO = driveService.getDrive(driveNum);
		List<DeptDTO> deptList = driveService.deptList();
		model.addAttribute("driveDTO", driveDTO);
		model.addAttribute("deptList", deptList);
		return "drive/create";
	}
	
	@PostMapping("{driveNum}/update")
	public String updateDrive(@Valid DriveDTO driveDTO,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "drive/create";
		}
		
		StaffDTO staffDTO = (StaffDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		driveDTO.setStaffDTO(staffDTO);
		driveDTO = driveService.updateDrive(driveDTO);
		if(driveDTO != null) {
			model.addAttribute("resultMsg", "드라이브 등록 완료");
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/drive/" + driveDTO.getDriveNum());
		} else {
			model.addAttribute("resultMsg", "드라이브 등록 실패");
			model.addAttribute("resultIcon", "error");
			model.addAttribute("resultUrl", "/drive/create");
		}
		return "common/result";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public DriveDTO deleteDrive(Long driveNum) {
		DriveDTO driveDTO = new DriveDTO();
		driveDTO.setDriveNum(driveNum);
		// 논리적 삭제
		driveDTO = driveService.deleteDrive(driveDTO); 
		
		if(driveDTO == null) {
			return null;
		}
		return driveDTO;
	}
	
	@GetMapping("{driveNum}/upload")
	public String uploadDocument(DriveDTO driveDTO) {
		System.out.println(driveDTO.getDriveNum());
		return "drive/document/add";
	}
	
	
}

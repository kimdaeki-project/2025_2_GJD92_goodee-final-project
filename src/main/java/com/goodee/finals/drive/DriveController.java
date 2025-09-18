package com.goodee.finals.drive;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.staff.StaffDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/drive/**")
@Slf4j
public class DriveController {

    private final AttachmentRepository attachmentRepository;

	@Autowired
	private DriveService driveService;

    DriveController(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }
	
    @ModelAttribute
    public void driveList(Model model) {
    	StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriveDTO> myDriveList = driveService.myDrive(staffDTO);
		List<DriveShareDTO> shareDriveList = driveService.shareDrive(staffDTO);
		
		model.addAttribute("myDriveList", myDriveList);
		model.addAttribute("shareDriveList", shareDriveList);
		model.addAttribute("staffDTO", staffDTO);
    }
    
	@GetMapping
	public String list(Model model) {
		return "drive/list";
	}
	
	@GetMapping("create")
	public String create(@ModelAttribute DriveDTO driveDTO) {
		return "drive/create";
	}
	
	@PostMapping("create")
	public String createDrive(@Valid DriveDTO driveDTO,BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
	        return "drive/create";
	    }
		
		// TODO - 드라이브 생성자정보가 필요해서 로그인체크용 - 나중에 삭제
		StaffDTO staffDTO = null;
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
		driveDTO = driveService.createDrive(driveDTO);
		
		if(driveDTO == null) {
			model.addAttribute("resultMsg", "중복된 이름");
			model.addAttribute("resultIcon", "error");
			model.addAttribute("resultUrl", "/drive/create");
			return "common/result";
		} 
		
		model.addAttribute("resultMsg", "드라이브 등록 완료");
		model.addAttribute("resultIcon", "success");
		model.addAttribute("resultUrl", "/drive");
		return "common/result";
	}
	
	@GetMapping("staffList")
	@ResponseBody
	public List<StaffDTO> staffList() {
		List<StaffDTO> list = driveService.staffList();
//		for (StaffDTO staffDTO : list) {
//			System.out.println(staffDTO.getDeptDTO().getDeptDetail());
//		}
		return list;
	}
	
}

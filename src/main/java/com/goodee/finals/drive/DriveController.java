package com.goodee.finals.drive;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.JobDTO;
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
	
    @ModelAttribute
    public void sideBarDriveList(Model model) {
    	StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		List<DriveDTO> myDriveList = driveService.getAllMyDrive(staffDTO);
		List<DriveShareDTO> shareDriveList = driveService.getShareDriveByStaffCode(staffDTO);
		
		if(ObjectUtils.isEmpty(myDriveList)) {
			DriveDTO driveDTO = driveService.createDefaultDrive(staffDTO);
			myDriveList.add(driveDTO);
		}
		
		model.addAttribute("staffDTO", staffDTO);
		model.addAttribute("myDriveList", myDriveList);
		model.addAttribute("shareDriveList", shareDriveList);
    }
	
	@GetMapping("staffList")
	@ResponseBody
	public List<StaffResponseDTO> staffList() {
		List<StaffResponseDTO> list = driveService.staffList();
		return list;
	}
    
	@GetMapping({ "", "{driveNum}" })
	public String getDrive(@PathVariable(required = false) Long driveNum, DrivePager drivePager, Model model, 
		@PageableDefault(size = 15, sort = "docNum", direction = Sort.Direction.DESC) Pageable pageable) {
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DriveDTO driveDTO = null;
	    if (driveNum == null) { // 최초 진입시 기본 드라이브
	        driveDTO = driveService.getDefaultDrive(staffDTO);
	    } else { 				// 특정 드라이브 진입
	        driveDTO = driveService.getDrive(driveNum);
	    }
	    
		Page<DocumentDTO> docList = driveService.getDocListByDriveNum(driveDTO, drivePager, staffDTO, pageable);
		
	    model.addAttribute("docList", docList);
	    model.addAttribute("driveDTO", driveDTO);
	    model.addAttribute("pager", drivePager);
	    model.addAttribute("jobList", driveService.getJobList());

	    return "drive/detail";
	}
	
	@GetMapping("create")
	public String create(@ModelAttribute DriveDTO driveDTO, Model model) {
		List<DeptDTO> deptList = driveService.getDeptList();
		model.addAttribute("deptList", deptList);
		return "drive/create";
	}
	
	@PostMapping("create")
	public String createDrive(@Valid DriveDTO driveDTO,BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("deptList", driveService.getDeptList());
	        return "drive/create";
	    }
		StaffDTO staffDTO = (StaffDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		driveDTO.setStaffDTO(staffDTO);
		driveDTO = driveService.createDrive(driveDTO);
		
		String resultMsg = "드라이브 등록 실패";
		String resultIcon = "error";
		String resultUrl = "/drive/create";
		
		if(driveDTO != null) {
			resultMsg = "드라이브 등록 완료";
			resultIcon = "success";
			resultUrl = "/drive";
		} 
		model.addAttribute("resultUrl", resultUrl);
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		
		return "common/result";
	}
	
	@GetMapping("{driveNum}/update")
	public String updateDrive(@PathVariable Long driveNum, Model model) {
		DriveDTO driveDTO = driveService.getDrive(driveNum);
		List<DeptDTO> deptList = driveService.getDeptList();
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
		
		String resultMsg = "드라이브 등록 실패";
		String resultIcon = "error";
		
		if(driveDTO != null) {
			resultMsg = "드라이브 등록 완료";
			resultIcon = "success";
			String resultUrl = "/drive/" + driveDTO.getDriveNum();
			model.addAttribute("resultUrl", resultUrl);
		} 
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public DriveDTO deleteDrive(Long driveNum) {
		DriveDTO driveDTO = new DriveDTO();
		driveDTO.setDriveNum(driveNum);
		driveDTO = driveService.deleteDrive(driveDTO); 
		
		if(driveDTO == null) {
			return null;
		}
		return driveDTO;
	}
	
	@PostMapping("{driveNum}/upload")
	public String uploadDocument(@PathVariable Long driveNum, JobDTO jobDTO, MultipartFile attach, Model model) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DocumentDTO documentDTO = driveService.uploadDocument(driveNum, jobDTO, attach, staffDTO);
		
		String resultMsg = "파일 업로드 실패";
		String resultIcon = "error";
		
		if(documentDTO != null) {
			resultMsg = "파일 업로드 성공";
			resultIcon = "success";
			String resultUrl = "/drive/" + driveNum;
			model.addAttribute("resultUrl", resultUrl);
		}
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@PostMapping("{driveNum}/delete")
	@ResponseBody
	public boolean deleteDocument(@PathVariable Long driveNum ,Long[] attachNums) {
		if(attachNums.length < 1) return false;
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean result = driveService.deleteDocByAttachNum(staffDTO, attachNums);
		
		return result;
	}
	
	@GetMapping("{driveNum}/downloadDocument")
	public String downloadDocument(Long[] attachNums, @PathVariable Long driveNum, Model model) {
		String type = "document";
		
		if (attachNums.length == 1) {
			AttachmentDTO file = driveService.getAttachByAttachNum(attachNums[0]);
			model.addAttribute("file", file);
			model.addAttribute("type", type);
			model.addAttribute("driveNum", driveNum);
			return "fileDownView";
		} else {
			List<AttachmentDTO> files = driveService.getAttachListByAttachNum(Arrays.asList(attachNums));
			model.addAttribute("files", files);
			model.addAttribute("type", type);
			model.addAttribute("driveNum", driveNum);
			return "zipDownView";
		}
		
	}
	
}

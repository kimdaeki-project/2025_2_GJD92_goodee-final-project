package com.goodee.finals.inspection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.validation.Valid;

@RequestMapping("/inspection/**")
@Controller
public class InspectionController {
	
	@Autowired
	private InspectionService inspectionService;
	
	// 어트랙션 점검 리스트 조회
	@GetMapping("")
	public String list(@PageableDefault(size = 10, sort = "isptNum", direction = Sort.Direction.DESC) Pageable pageable, InspectionPager inspectionPager, Model model, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(required = false, defaultValue = "") String searchType) throws Exception {
		Page<InspectionDTO> resultInspection = inspectionService.list(pageable, searchType, keyword);
		inspectionPager.setKeyword(keyword);
		inspectionPager.calc(resultInspection);
		
		model.addAttribute("inspection", resultInspection);
		model.addAttribute("pager", inspectionPager);
		model.addAttribute("totalInspection", resultInspection.getContent().size());
		model.addAttribute("searchType", searchType);
		
		return "inspection/inspectionList";
	}
	
	
	// 어트랙션 점검 리스트 작성
	@GetMapping("write")
	public String writeInspection(Model model) throws Exception {
		model.addAttribute("inspectionDTO", new InspectionDTO());
		
		// 어트랙션 목록
		List<RideDTO> rideList = inspectionService.getAllRides(); 
	    model.addAttribute("rideList", rideList);

	    // 시설팀 직원 목록 (dept_code=1003)
	    List<StaffDTO> staffList = inspectionService.getStaffList();
	    model.addAttribute("staffList", staffList);
		
		model.addAttribute("mode", "add");
		
		return "inspection/inspectionWrite";
	}
	
	// 어트랙션 점검 리스트 작성
	@PostMapping("write")
	public String writeInspection(@Valid InspectionDTO inspectionDTO, BindingResult bindingResult, MultipartFile attach, Model model) throws Exception {
		boolean result = inspectionService.writeInspection(inspectionDTO, attach);
		
		String resultMsg = "어트랙션 점검 기록 등록에 실패했습니다.";
		String resultIcon = "warning";
			
		if (result) {
			resultMsg = "어트랙션 점검 기록 등록을 완료하였습니다.";
			resultIcon = "success";
			
			// 삭제 완료 후 목록 페이지로 이동
			String resultUrl = "/inspection/";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";  // 삭제 후 목록으로 이동
	}
	
	
	// 어트랙션 점검 기록 조회 상세
	@GetMapping("{isptNum}")
	public String getispt(@PathVariable("isptNum") Integer isptNum, Model model) throws Exception {
		InspectionDTO inspectionDTO = inspectionService.getIsptByNum(isptNum);
		
		model.addAttribute("inspection", inspectionDTO);
		
		return "inspection/inspectionDetail";
	}
	
	
	
	// 어트랙션 점검 기록 수정
	// 어트랙션 점검 기록 수정 폼으로 이동(기존 입력된 데이터를 가지고 감)
	// GET/update : 기존 정보 + 첨부파일 attachmentDTO 모델에 넣어줌
	@GetMapping("{isptNum}/update")
	public String getIsptUpdate(@PathVariable("isptNum") Integer isptNum, Model model) throws Exception {
		// 기존 데이터 조회
		InspectionDTO inspectionDTO = inspectionService.getIsptByNum(isptNum);
		model.addAttribute("inspectionDTO", inspectionDTO);
		model.addAttribute("mode", "edit");  // 수정 모드
		
		// 첨부파일이 있으면 JSP에 따로 전달
		if (inspectionDTO.getInspectionAttachmentDTO() != null) {
			model.addAttribute("attachmentDTO", inspectionDTO.getInspectionAttachmentDTO().getAttachmentDTO());
		}
		
		// 어트랙션 목록
		List<RideDTO> rideList = inspectionService.getAllRides(); 
	    model.addAttribute("rideList", rideList);

	    // 시설팀 직원 목록 (dept_code = 1003)
	    List<StaffDTO> staffList = inspectionService.getStaffList();
	    model.addAttribute("staffList", staffList);
		
		// 등록/수정 모든 구분값 내려주기
		model.addAttribute("mode", "edit");
		return "inspection/inspectionWrite";
	}
	
	
	// 어트랙션 수정
	// POST/update : MultipartFile attach 같이 받음
	@PostMapping("{isptNum}/update")
	public String postIsptUpdate(@Valid InspectionDTO inspectionDTO, BindingResult bindingResult, MultipartFile attach, Model model) throws Exception {
		boolean result = inspectionService.updateInspection(inspectionDTO, attach);
		
		String resultMsg = "어트랙션 점검 기록 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "어트랙션 점검 기록을 수정했습니다.";
			resultIcon = "success";
			
			String resultUrl = "/inspection/" + inspectionDTO.getIsptNum();
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	
	// 어트랙션 점검 기록 삭제
	@PostMapping("{isptNum}/delete")
	public String deleteInspection(@PathVariable("isptNum") Integer isptNum, Model model) throws Exception {
		InspectionDTO result = inspectionService.deleteInspection(isptNum);
		
		String resultMsg = "어트랙션 점검 기록 삭제 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "어트랙션 점검 기록을 삭제하였습니다.";
			resultIcon = "success";
			
			// 삭제 완료 후 목록 페이지로 이동
			String resultUrl = "/inspection/";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	
	// 체크리스트 파일 다운로드
	@GetMapping("{attachNum}/download")
	public String download(@PathVariable("attachNum") AttachmentDTO attachmentDTO, Model model) throws Exception {
		AttachmentDTO result = inspectionService.download(attachmentDTO);
		model.addAttribute("file", result);
		model.addAttribute("type", "inspection");
		
		return "fileDownView";
	}
	
	
	
	
	

}
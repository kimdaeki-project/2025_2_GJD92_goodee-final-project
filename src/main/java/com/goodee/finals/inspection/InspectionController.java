package com.goodee.finals.inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RequestMapping("/inspection/**")
@Controller
public class InspectionController {
	
	@Autowired
	private InspectionService inspectionService;
	
	// 어트랙션 점검 리스트 조회
	@GetMapping("")
	public String list(@PageableDefault(size = 5, sort = "isptNum", direction = Sort.Direction.DESC) Pageable pageable, InspectionPager inspectionPager, Model model) throws Exception {
		String keyword = inspectionPager.getKeyword();
		if (keyword == null) keyword = "";
		
		Page<InspectionDTO> resultInspection = inspectionService.list(pageable, keyword);
		inspectionPager.calc(resultInspection);
		model.addAttribute("inspection", resultInspection);
		model.addAttribute("pager", inspectionPager);
		model.addAttribute("totalInspection", resultInspection.getContent().size());
		
		return "inspection/inspectionList";
	}
	
	
	// 어트랙션 점검 리스트 작성
	@GetMapping("write")
	public String writeInspection(Model model) throws Exception {
		model.addAttribute("inspectionDTO", new InspectionDTO());
		
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

}
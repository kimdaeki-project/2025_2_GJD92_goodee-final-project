package com.goodee.finals.fault;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("/fault/**")
@Controller
public class FaultController {

	
	 @Autowired 
	 private FaultService faultService;
	
	 // 어트랙션 고장 신고 리스트 조회
	 @GetMapping("")
	 public String list(
	         @PageableDefault(size = 10, sort = "faultNum", direction = Direction.DESC) Pageable pageable,
	         FaultPager faultPager,
	         Model model,
	         @RequestParam(required = false, defaultValue = "") String keyword,
	         @RequestParam(required = false, defaultValue = "") String searchType,
	         @RequestParam(required = false, defaultValue = "") String keywordState) throws Exception {

	     // 검색 조건이 state일 경우 keyword를 keywordState 값으로 교체
	     if ("state".equals(searchType) && keywordState != null && !keywordState.isEmpty()) {
	         keyword = keywordState;
	     }

	     Page<FaultDTO> resultFault = faultService.list(pageable, searchType, keyword);
	     faultPager.setKeyword(keyword);
	     faultPager.calc(resultFault);

	     model.addAttribute("fault", resultFault);
	     model.addAttribute("pager", faultPager);
	     model.addAttribute("searchType", searchType);
	     model.addAttribute("totalFault", resultFault.getContent().size());

	     return "fault/faultList";
	 }
	
	
	// 어트랙션 고장 신고 등록
	@GetMapping("write")
	public  String writeFault(Model model) throws Exception {
		model.addAttribute("faultDTO", new FaultDTO());
		
		// 어트랙션 목록
		List<RideDTO> rideList = faultService.getAllRides();
		model.addAttribute("rideList", rideList);
		
		model.addAttribute("mode", "add");	
		
		
		return "fault/faultWrite";
	}
	
	
	// 어트랙션 고장 신고 등록
	@PostMapping("write")
	public String writeFault(@Valid @ModelAttribute("faultDTO") FaultDTO faultDTO, BindingResult bindingResult, Model model) throws Exception {
	    // 유효성 검증 실패 시 다시 폼으로
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("rideList", faultService.getAllRides());
	        return "fault/faultWrite";
	    }

	    boolean result = faultService.writeFault(faultDTO);

	    String resultMsg = "어트랙션 고장 신고 등록에 실패했습니다.";
	    String resultIcon = "warning";
	    String resultUrl = "/fault/";

	    if (result) {
	        resultMsg = "어트랙션 고장 신고 등록을 완료하였습니다.";
	        resultIcon = "success";
	    }

	    model.addAttribute("resultMsg", resultMsg);
	    model.addAttribute("resultIcon", resultIcon);
	    model.addAttribute("resultUrl", resultUrl);

	    return "common/result";
	}

	
	
	// 어트랙션 고장 신고 기록 조회 상세
	@Transactional
	@GetMapping("{faultNum}")
	public String getFault(@PathVariable("faultNum") Integer faultNum, Model model) throws Exception {
		FaultDTO faultDTO = faultService.getFaultByNum(faultNum);
		
		model.addAttribute("fault", faultDTO);
		
		return "fault/faultDetail";
	}
	
	
	// 어트랙션 고장 신고 수정
	// 어트랙션 고장 신고 수정 픔오르 이동(기존 입력된 데이터를 가지고 감)
	// GET/update : 기존 정보 + 첨부파일 attachmentDTO 모델에 넣어줌
	@GetMapping("{faultNum}/update")
	public String getFaultUpdate(@PathVariable("faultNum") Integer faultNum, Model model) throws Exception {
		// 기존 데이터 조회
		FaultDTO faultDTO = faultService.getFaultByNum(faultNum);
		model.addAttribute("faultDTO", faultDTO);
		
		// 시설팀 직원 목록 (dept_code = 1003)
		List<StaffDTO> staffList = faultService.getStaffList();
		model.addAttribute("staffList", staffList);
		
		// 등록/ 수정 모든 구분값 내려주기
		return "fault/faultUpdate";
	}
	
	
	// 어트랙션 수정
	// POST/update : MultipartFile attach 같이 받음
	@PostMapping("{faultNum}/update")
	public String postFaultUpdate(@Valid FaultDTO faultDTO, BindingResult bindingResult, MultipartFile attach, Model model) throws Exception {
		boolean result = faultService.updateFault(faultDTO, attach);
		
		String resultMsg = "어트랙션 고장 신고 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "어트랙션 고장 신고 수정을 완료했습니다.";
			resultIcon = "success";
			
			String resultUrl = "/fault/" + faultDTO.getFaultNum();
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
				
	}
	
	
	// 어트랙션 고장 신고 삭제
	@PostMapping("{faultNum}/delete")
	public String deleteFault(@PathVariable("faultNum") Integer faultNum, Model model) throws Exception {
		 FaultDTO result = faultService.deleteFault(faultNum);
		 
		 String resultMsg = "어트랙션 고장 신고 삭제 중 오류가 발생했습니다.";
		 String resultIcon = "warning";
			
			if (result != null) {
				resultMsg = "어트랙션 고장 신고를 삭제하였습니다.";
				resultIcon = "success";
				
				// 삭제 완료 후 목록 페이지로 이동
				String resultUrl = "/fault/";
				model.addAttribute("resultUrl", resultUrl);
			}
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
	}
	
	
	
	
	
	
}

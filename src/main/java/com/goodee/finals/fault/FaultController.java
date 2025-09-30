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

import com.goodee.finals.ride.RideDTO;

import jakarta.validation.Valid;

@RequestMapping("/fault/**")
@Controller
public class FaultController {

	
	 @Autowired 
	 private FaultService faultService;
	
	// 어트랙션 고장 신고 리스트 조회
	 @GetMapping("")
	 public String list(@PageableDefault(size =10, sort = "faultNum", direction = Direction.DESC) Pageable pageable, FaultPager faultPager, Model model, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(required = false, defaultValue = "") String searchType) throws Exception {
		 Page<FaultDTO> resultFault = faultService.list(pageable, searchType, keyword);
		 faultPager.setKeyword(keyword);
		 faultPager.calc(resultFault);
		 
		 model.addAttribute("fault", resultFault);
		 model.addAttribute("pager", faultPager);
		 model.addAttribute("totalFault", resultFault.getContent().size());
		 model.addAttribute("searchType", searchType);
		 
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
	public String writeFault(
	        @Valid @ModelAttribute("faultDTO") FaultDTO faultDTO,
	        BindingResult bindingResult,
	        Model model) throws Exception {

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
	@GetMapping("{faultNum}")
	public String getFault(@PathVariable("faultNum") Integer faultNum, Model model) throws Exception {
		FaultDTO faultDTO = faultService.getFaultByNum(faultNum);
		
		model.addAttribute("fault", faultDTO);
		
		return "fault/faultDetail";
	}
	
	
	
	
	
}

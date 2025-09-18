package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RequestMapping("/ride/**")
@Controller
public class RideController {

    private final RideRepository rideRepository;
	
	@Autowired
	private RideService rideService;

    RideController(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
	
	// 어트랙션 전체 조회
    @GetMapping("")
    public String getAllRides(Model model) throws Exception {
        List<RideDTO> result = rideService.getAllRides();
        model.addAttribute("ride", result);

        model.addAttribute("railRides", rideRepository.findByRideTypeAndRideDeletedFalse("A11"));
        model.addAttribute("rotationRides", rideRepository.findByRideTypeAndRideDeletedFalse("B11"));
        model.addAttribute("waterRides", rideRepository.findByRideTypeAndRideDeletedFalse("C11"));
        model.addAttribute("viewRides", rideRepository.findByRideTypeAndRideDeletedFalse("D11"));
        model.addAttribute("kidsRides", rideRepository.findByRideTypeAndRideDeletedFalse("E11"));

        return "ride/rideList";
    }

	
	// 어트랙션 등록 폼
	@GetMapping("add")
	public String registRide(Model model) throws Exception {
		model.addAttribute("rideDTO", new RideDTO());  // JSP에서 사용할 rideDTO 객체
		model.addAttribute("mode", "add");  // 등록 모드
		
		return "ride/rideAdd";  //jsp 파일명
	}
	

	
	// 어트랙션 등록 처리
	// rideDTO 값 받아서 DB 저장
	@PostMapping("add")  
	public String registRide(@Valid RideDTO rideDTO, BindingResult bindingResult, MultipartFile attach, Model model) throws Exception{
		boolean result = rideService.registRide(rideDTO, attach);
		
		String resultMsg = "어트랙션 등록에 실패했습니다.";
		String resultIcon = "warning";
			
		if (result) {
			resultMsg = "어트랙션 등록을 완료하였습니다.";
			resultIcon = "success";
			
			// 삭제 완료 후 목록 페이지로 이동
			String resultUrl = "/ride/";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";  // 삭제 후 목록으로 이동
	}
	
	// 어트랙션 상세조회
	@GetMapping("{rideCode}")
	public String getRides(@PathVariable("rideCode") String rideCode, Model model) throws Exception {
	    RideDTO ride = rideService.getRideById(rideCode);
	    
	    model.addAttribute("ride", ride);
	    
	    return "ride/rideDetail"; // JSP
	}

	
	
	// 어트랙션 수정
	// 어트랙션 수정 폼으로 이동(기존 입력된 데이터 가지고 감)
	// GET/update : 기존 정보 + 첨부파일 attachmentDTO 모델에 넣어줌
	@GetMapping("{rideCode}/update")
	public String getRideUpdate(@PathVariable("rideCode") String rideCode, Model model) throws Exception {
		// 기존 데이터 조회
		RideDTO rideDTO = rideService.getRideById(rideCode);
		model.addAttribute("rideDTO", rideDTO);
		model.addAttribute("mode", "edit");  // 수정 모드
		
		// 첨부파일 있으면 JSP에 따로 전달
	    if (rideDTO.getRideAttachmentDTO() != null) {
	        model.addAttribute("attachmentDTO", rideDTO.getRideAttachmentDTO().getAttachmentDTO());
	    }

	    // 등록/수정 모드 구분값 내려주기
	    model.addAttribute("mode", "edit");
	    return "ride/rideAdd";
		
	 }
	
	// 어트랙션 수정
	// POST/update : MultipartFile attach 같이 받음
	@PostMapping("{rideCode}/update")
	public String postRideUpdate(@Valid RideDTO rideDTO, BindingResult bindingResult, MultipartFile attach, Model model) throws Exception {
		boolean result = rideService.updateRide(rideDTO, attach);
		
		String resultMsg = "어트랙션 정보 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "어트랙션 정보를 수정했습니다.";
			resultIcon = "success";
			
			String resultUrl = "/ride/" + rideDTO.getRideCode();
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	
	// 어트랙션 삭제
	@PostMapping("{rideCode}/delete")
	public String deleteRide(@PathVariable("rideCode") String rideCode, Model model) throws Exception {
		RideDTO result = rideService.deleteRide(rideCode);
		
		String resultMsg = "어트랙션 삭제 중 오류가 발생했습니다.";
		String resultIcon = "warning";
 		
		if (result != null) {
			resultMsg = "어트랙션을 삭제하였습니다.";
			resultIcon = "success";
			
			// 삭제 완료 후 목록 페이지로 이동
			String resultUrl = "/ride/";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";  // 삭제 후 목록으로 이동
		
	}
	

	// 어트랙션 코드 중복 검사 (AJAX로 요청)
	@GetMapping("/ride/checkCode")
	@ResponseBody
	public boolean checkRideCode(@RequestParam String rideCode) throws Exception {
		return rideService.isDuplicateRideCode(rideCode);
	}
	

	
	
	
}

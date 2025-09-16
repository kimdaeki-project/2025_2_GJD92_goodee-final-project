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
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RequestMapping("/ride/**")
@Controller
public class RideController {
	
	@Autowired
	private RideService rideService;
	
	// 어트랙션 전체 조회
	@GetMapping("")
	public String getAllRides(Model model) throws Exception {
		List<RideDTO> result =  rideService.getAllRides();
		model.addAttribute("ride", result);
		return "ride/rideList";
	}
	
	// 어트랙션 등록 폼
	@GetMapping("add")
	public String addRide(Model model) throws Exception {
		model.addAttribute("rideDTO", new RideDTO());  // JSP에서 사용할 rideDTO 객체
		return "ride/rideAdd";  //jsp 파일명
	}
	
	// 어트랙션 등록 처리
	// rideDTO 값 받아서 DB 저장
	@PostMapping("add")  
	public String saveRide(@Valid RideDTO rideDTO, BindingResult bindingResult, MultipartFile attach) throws Exception{
		rideService.saveRide(rideDTO, attach);
		return "ride/rideList";
	}
	
	// 어트랙션 상세조회
	@GetMapping("detail")
	public String getRides(@PathVariable("rideCode") Integer rideCode, Model model) throws Exception {
		RideDTO ride =  rideService.getRideById(rideCode);
		model.addAttribute("ride", ride);
		return "ride/rideDetail";
	}
	
	
	// 어트랙션 수정

	
	
	// 어트랙션 삭제
	public String deleteRide(@PathVariable("rideCode") Integer rideCode) throws Exception {
		rideService.deleteRide(rideCode);
		return "ride/rideList";
		
	}
	

	
	
	
	
	
}

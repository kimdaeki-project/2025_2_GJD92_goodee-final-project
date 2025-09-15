package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("add")  // 어트랙션 등록
	public String addRide(Model model) throws Exception {
		model.addAttribute("rideDTO", new RideDTO());  // JSP에서 사용할 rideDTO 객체
		return "ride/rideAdd";  //jsp 파일명
	}
	
	@PostMapping("add")  // rideDTO 값 받아서 DB 저장
	public String saveRide(@ModelAttribute RideDTO rideDTO) throws Exception{
		return "ride/rideList";
	}
	
	@GetMapping("detail")
	public String getRides(Model model) throws Exception {
		List<RideDTO> result =  rideService.getAllRides();
		model.addAttribute("ride", result);
		return "ride/rideDetail";
	}
	
//	@GetMapping("{rideCode}")
//	public String getRides(@PathVariable("rideCode") Integer rideCode) throws Exception {
//		return "ride/rideDetail";
//	}
	
	// 어트랙션 수정
//	@PutMapping("/{id}")
//	public ResponseEntity<RideDTO> updateRide(@PathVariable("id") Long id, @RequestBody RideDTO rideDTO) throws Exception {
//		// 기존 데이터 확인
//		rideDTO existRide = rideService.getRideById(id);
//	}
	
	
	// 어트랙션 삭제
	public void deleteRide(@PathVariable("rideCode") Long rideCode) throws Exception {
		
	}
	
	
	
	
	
}

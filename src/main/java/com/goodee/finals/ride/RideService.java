package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideService {
	
	@Autowired
	private RideRepository rideRepository;
	
	// 어트랙션 전체 조회
	public List<RideDTO> getAllRides() throws Exception {
		return rideRepository.findAll();
	}
	
	// 등록/수정
	public RideDTO saveRide(RideDTO rideDTO) throws Exception {
		return rideRepository.save(rideDTO);
	}
	
	// 삭제
	public void deleteRide(Long rideCode) throws Exception {
		rideRepository.deleteById(rideCode);
	}

}

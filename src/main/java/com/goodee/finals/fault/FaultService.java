package com.goodee.finals.fault;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.ride.RideRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FaultService {

    private final RideRepository rideRepository;
	
	@Autowired
	private FaultRepository faultRepository;


    FaultService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
	
	
	// 어트랙션 고장 신고 리스트 조회
    public Page<FaultDTO> list (Pageable pageable, String searchType, String keyword) throws Exception {
    	
    	// 키워드가 없는 경우(전체 조회)
    	if (keyword == null || keyword.trim().isEmpty()) {
    		return faultRepository.findAllByFaultDeleteFalse(pageable);
    	}
    	
    	// 어트랙션 이름 검색
    	else if ("ride".equals(searchType)) {
    		return faultRepository.findByRide(keyword, pageable);
    	}
    	
    	// 신고 제목 검색
    	else if ("title".equals(searchType)) {
    		return faultRepository.findByTitle(keyword, pageable);
    	}
    	
    	// 담당자 검색
    	else if ("staff".equals(searchType)) {
    		return faultRepository.findByStaff(keyword, pageable);
    	}
    	
    	// 신고 유형 검색
    	else if ("state".equals(searchType)) {                      
    		return faultRepository.findByState(Integer.parseInt(keyword), pageable);
    	} else {
    		//기본 검색 (키워드 없음)
    		return faultRepository.findAllByFaultDeleteFalse(pageable);
    	}
    }
	
	
	
	// 어트랙션 고장 신고 작성
	@Transactional
	public boolean writeFault(FaultDTO faultDTO) throws Exception {
		
		try {
			// 선택된 어트랙션을 DB에서 조회
			RideDTO rideDTO = rideRepository.findById(faultDTO.getRideDTO().getRideCode())
					.orElseThrow(() -> new RuntimeException("해당 어트랙션이 존재하지 않습니다."));
					
			// faultDTO에 ride와 담당자 자동 세팅
			faultDTO.setRideDTO(rideDTO);
			faultDTO.setStaffDTO(rideDTO.getStaffDTO());
			
			// 저장
			FaultDTO save = faultRepository.save(faultDTO);
			
			return save != null && save.getFaultNum() != null;
					
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	// 어트랙션 고장 신고 단일 조회
	public FaultDTO getFaultByNum(Integer faultNum) throws Exception {
		return faultRepository.findById(faultNum).orElse(null);
	}
	
	
	
	
	// 어트랙션 전체 목록 가져오기
	public List<RideDTO> getAllRides() {
		return rideRepository.findByRideDeletedFalse();  // 삭제되지 않은 것만
	}
	

}

package com.goodee.finals.fault;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.FaultAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.ride.RideRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FaultService {

	@Autowired
    private final AttachmentRepository attachmentRepository;
	
	@Autowired
    private final StaffRepository staffRepository;

	@Autowired
    private final RideRepository rideRepository;
	
	@Autowired
	private FaultRepository faultRepository;
	
	@Autowired
    private FileService fileService;


    FaultService(RideRepository rideRepository, StaffRepository staffRepository, AttachmentRepository attachmentRepository) {
        this.rideRepository = rideRepository;
        this.staffRepository = staffRepository;
        this.attachmentRepository = attachmentRepository;
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
	public FaultDTO writeFault(FaultDTO faultDTO) throws Exception {
		
		try {
			// 선택된 어트랙션을 DB에서 조회
			RideDTO rideDTO = rideRepository.findById(faultDTO.getRideDTO().getRideCode())
					.orElseThrow(() -> new RuntimeException("해당 어트랙션이 존재하지 않습니다."));
					
			// faultDTO에 ride와 담당자 자동 세팅
			faultDTO.setRideDTO(rideDTO);
			faultDTO.setStaffDTO(rideDTO.getStaffDTO());
			
			// 신고 상태는 (410: 신고접수)로 고정값 세팅
			faultDTO.setFaultState(410);
			
			// 저장
			FaultDTO save = faultRepository.save(faultDTO);
			
			if (save != null && save.getFaultNum() != null) {
				return save;
			} else {
				return null;
			}
					
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	// 어트랙션 고장 신고 단일 조회
	public FaultDTO getFaultByNum(Integer faultNum) throws Exception {
		return faultRepository.findById(faultNum).orElse(null);
	}
	
	
	// 어트랙션 고장 신고 수정
	public boolean updateFault(FaultDTO faultDTO, MultipartFile attach) throws Exception {
		FaultDTO origin = faultRepository.findById(faultDTO.getFaultNum())
				.orElseThrow();
		
		// 기본 필드 업데이트
		origin.setRideDTO(faultDTO.getRideDTO());
		origin.setFaultTitle(faultDTO.getFaultTitle());
		origin.setFaultContent(faultDTO.getFaultContent());
		origin.setFaultDate(faultDTO.getFaultDate());
		origin.setStaffDTO(faultDTO.getStaffDTO());
		origin.setFaultState(faultDTO.getFaultState());
		
		// 첨부파일 처리
	    if (attach != null && attach.getSize() > 0) {
	        String fileName = fileService.saveFile(FileService.FAULT, attach);

	        AttachmentDTO attachmentDTO = new AttachmentDTO();
	        attachmentDTO.setAttachSize(attach.getSize());
	        attachmentDTO.setOriginName(attach.getOriginalFilename());
	        attachmentDTO.setSavedName(fileName);

	        attachmentRepository.save(attachmentDTO);

	        FaultAttachmentDTO faultAttachmentDTO = new FaultAttachmentDTO();
	        faultAttachmentDTO.setFaultDTO(origin); // 기존 origin에 연결
	        faultAttachmentDTO.setAttachmentDTO(attachmentDTO);

	        origin.setFaultAttachmentDTO(faultAttachmentDTO);
	    }

	    // 저장 (origin 사용)
	    FaultDTO result = faultRepository.save(origin);

	    return result != null;
		
	}
	
	
	// 어트랙션 고장 신고 삭제 (논리적 삭제)
	@Transactional
	public FaultDTO deleteFault(Integer faultNum) throws Exception {
		// DB에서 엔터티 조회
		FaultDTO fault = faultRepository.findById(faultNum)
				.orElseThrow(() -> new IllegalArgumentException("해당 어트랙션 고장 신고가 존재하지 않습니다."));
		
		// 논리적 삭제 처리
		fault.setFaultDelete(true);
		
		// save() 호출에서 업데이트 반영
		return faultRepository.save(fault);
		
	}
 	
	
	
	
	
	// 어트랙션 전체 목록 가져오기
	public List<RideDTO> getAllRides() {
		return rideRepository.findByRideDeletedFalse();  // 삭제되지 않은 것만
	}
	
	// 시설팀 직원 목록 가져오기 (dept_code = 1003)
	public List<StaffDTO> getStaffList() {
		return staffRepository.findByDeptDTO_DeptCodeAndStaffEnabledTrue(1003);
	}

}

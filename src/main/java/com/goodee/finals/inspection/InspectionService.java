package com.goodee.finals.inspection;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.InspectionAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.ride.RideRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InspectionService {
	
	@Autowired
	private InspectionRepository inspectionRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
    private StaffRepository staffRepository;
	
	@Autowired
    private RideRepository rideRepository;
	
	@Autowired
    private FileService fileService;
	
	// 어트랙션 점검 리스트 조회
	public Page<InspectionDTO> list(Pageable pageable, String searchType, String keyword) throws Exception {
		
		// 키워드가 없는 경우(전체 조회)
		if (keyword == null || keyword.trim().isEmpty()) {
			return inspectionRepository.findAllByIsptDeleteFalse(pageable);
		} 
		// 어트랙션 이름 검색
		else if ("ride".equals(searchType)) {  
			return inspectionRepository.findByRide(keyword, pageable);
		} 
		// 점검유형 검색
		else if ("type".equals(searchType)) {  
			return inspectionRepository.findByType(Integer.parseInt(keyword), pageable);
		} 
		// 점검결과 검색
		else if ("result".equals(searchType)) {
			return inspectionRepository.findByResult(Integer.parseInt(keyword), pageable);
		} 
		// 담당자 아름 검색
		else if ("staff".equals(searchType)) {
			return inspectionRepository.findByStaff(keyword, pageable);
		} else {
			// 기본검색 (키워드 없음)
			return inspectionRepository.findAllByIsptDeleteFalse(pageable);
		}
	}
	
	
	// 어트랙션 점검 기록 작성
	@Transactional
	public boolean writeInspection(InspectionDTO inspectionDTO, MultipartFile attach) throws Exception {
		String fileName = null;
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		
		if (attach != null && attach.getSize() > 0) {
			try {
				fileName = fileService.saveFile(FileService.INSPECTION, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		InspectionAttachmentDTO inspectionAttachmentDTO = new InspectionAttachmentDTO();
		inspectionAttachmentDTO.setInspectionDTO(inspectionDTO);
		inspectionAttachmentDTO.setAttachmentDTO(attachmentDTO);
		
		inspectionDTO.setInspectionAttachmentDTO(inspectionAttachmentDTO);
		InspectionDTO result = inspectionRepository.save(inspectionDTO);
		
		if (result != null) return true;
		else return false;
		
	}
	
	
	// 어트랙션 점검 기록 단일 조회
	public InspectionDTO getIsptByNum(Integer isptNum) throws Exception {
		return inspectionRepository.findById(isptNum).orElse(null);
	}
	
	
	// 어트랙션 점검 기록 수정
	public boolean updateInspection(InspectionDTO inspectionDTO, MultipartFile attach) throws Exception {
		InspectionDTO origin = inspectionRepository.findById(inspectionDTO.getIsptNum())
				.orElseThrow();
		
		// 기본 필드 업데이트
		origin.setRideDTO(inspectionDTO.getRideDTO());
		origin.setIsptType(inspectionDTO.getIsptType());
		origin.setStaffDTO(inspectionDTO.getStaffDTO());
		origin.setIsptResult(inspectionDTO.getIsptResult());
		origin.setIsptStart(inspectionDTO.getIsptStart());
		origin.setIsptEnd(inspectionDTO.getIsptEnd());
		
		if (attach != null && attach.getSize() > 0) {
			InspectionDTO before = inspectionRepository.findById(inspectionDTO.getIsptNum()).orElseThrow();
			AttachmentDTO beforeAttach = before.getInspectionAttachmentDTO().getAttachmentDTO();
			attachmentRepository.deleteById(beforeAttach.getAttachNum());
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			
			try {
				String fileName = fileService.saveFile(FileService.INSPECTION, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
				
				InspectionAttachmentDTO inspectionAttachmentDTO = new InspectionAttachmentDTO();
				inspectionAttachmentDTO.setInspectionDTO(inspectionDTO);
				inspectionAttachmentDTO.setAttachmentDTO(attachmentDTO);
				
				inspectionDTO.setInspectionAttachmentDTO(inspectionAttachmentDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		InspectionDTO result = inspectionRepository.saveAndFlush(inspectionDTO);
		
		if (result != null) return true;
		else return false;
		
	}
	
	
	// 어트랙션 점검 기록 삭제 (논리적 삭제)
	@Transactional
	public InspectionDTO deleteInspection(Integer isptNum) throws Exception {
		// DB에서 엔터티 조회
		InspectionDTO ispt = inspectionRepository.findById(isptNum)
				.orElseThrow(() -> new IllegalArgumentException("해당 어트랙션 점검 기록이 존재하지 않습니다."));
		
		
		// 논리적 삭제 처리
		ispt.setIsptDelete(true);
		
		// save() 호출에서 업데이트 반영
		return inspectionRepository.save(ispt);
	}
	
	
	// 체크리스트 파일 다운로드
	public AttachmentDTO download(AttachmentDTO attachmentDTO) throws Exception {
		Optional<AttachmentDTO> result = attachmentRepository.findById(attachmentDTO.getAttachNum());
		return result.get();
	}
	
	
	// 어트랙션 전체 목록 가져오기
    public List<RideDTO> getAllRides() {
        return rideRepository.findByRideDeletedFalse(); // 삭제되지 않은 것만
    }

    // 시설팀 직원 목록 가져오기 (dept_code = 1003)
    public List<StaffDTO> getStaffList() {
        return staffRepository.findByDeptDTO_DeptCode(1003);
    }

	
	

}

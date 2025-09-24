package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.RideAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffController;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RideService {

	@Autowired
    private StaffController staffController;

	@Autowired
    private StaffRepository staffRepository;

	@Autowired
    private AttachmentRepository attachmentRepository;
	
	@Autowired
    private FileService fileService;
   
	@Autowired
	private RideRepository rideRepository;

    RideService(FileService fileService, AttachmentRepository attachmentRepository, StaffRepository staffRepository, StaffController staffController) {
        this.fileService = fileService;
        this.attachmentRepository = attachmentRepository;
        this.staffRepository = staffRepository;
    }
	
	// 어트랙션 전체 조회(리스트)
	public List<RideDTO> getAllRides() throws Exception {
		return rideRepository.findByRideDeletedFalse();
	}
	
	
    // 어트랙션 단일 조회
    public RideDTO getRideById(String rideCode) throws Exception {
        return rideRepository.findById(rideCode).orElse(null);
    }
	
	// 어트랙션 등록
 	public boolean registRide(RideDTO rideDTO, MultipartFile attach) throws Exception {	
 		String fileName = null;
 		AttachmentDTO attachmentDTO = new AttachmentDTO();
 		
 		if (attach != null && attach.getSize() > 0) {
 			try {
 				fileName = fileService.saveFile(FileService.RIDE, attach);
 				
 				attachmentDTO.setAttachSize(attach.getSize());
 				attachmentDTO.setOriginName(attach.getOriginalFilename());
 				attachmentDTO.setSavedName(fileName);
 				
 				attachmentRepository.save(attachmentDTO);
 			} catch (Exception e) {
 				e.printStackTrace();
			}
 		} 
 		
 		RideAttachmentDTO rideAttachmentDTO = new RideAttachmentDTO();
 		rideAttachmentDTO.setRideDTO(rideDTO);
 		rideAttachmentDTO.setAttachmentDTO(attachmentDTO);
 		
 		rideDTO.setRideAttachmentDTO(rideAttachmentDTO);
 		RideDTO result = rideRepository.save(rideDTO);
 		
 		if (result != null) return true;
 		else return false;
 	}

    
    // 수정
    public boolean updateRide(RideDTO rideDTO, MultipartFile attach) throws Exception {
    	RideDTO origin  = rideRepository.findById(rideDTO.getRideCode())
                .orElseThrow(() -> new Exception("수정할 어트랙션이 없습니다."));

    	// 기본 필드 업데이트
        origin.setRideName(rideDTO.getRideName());
        origin.setRideType(rideDTO.getRideType());
        origin.setRideDate(rideDTO.getRideDate());
        origin.setRideState(rideDTO.getRideState());
        origin.setRideInfo(rideDTO.getRideInfo());
        origin.setRideRule(rideDTO.getRideRule());
        origin.setRideCapacity(rideDTO.getRideCapacity());
        origin.setRideDuration(rideDTO.getRideDuration());
        origin.setStaffDTO(rideDTO.getStaffDTO());
        
        
        if (attach != null && attach.getSize() > 0) {
			RideDTO before = rideRepository.findById(rideDTO.getRideCode()).orElseThrow();
			AttachmentDTO beforeAttach = before.getRideAttachmentDTO().getAttachmentDTO();
			attachmentRepository.deleteById(beforeAttach.getAttachNum());
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			
			try {
				String fileName = fileService.saveFile(FileService.RIDE, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
				
				RideAttachmentDTO rideAttachmentDTO = new RideAttachmentDTO();
				rideAttachmentDTO.setRideDTO(rideDTO);
				rideAttachmentDTO.setAttachmentDTO(attachmentDTO);
				
				rideDTO.setRideAttachmentDTO(rideAttachmentDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        RideDTO result = rideRepository.saveAndFlush(rideDTO);
        
        if (result != null) return true;
        else return false;
    }
    
	
	// 삭제(논리적 삭제)
    @Transactional
    public RideDTO deleteRide(String rideCode) throws Exception {
        // 1. DB에서 엔티티 조회
        RideDTO ride = rideRepository.findById(rideCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 어트랙션이 존재하지 않습니다."));

        // 2. 논리삭제 처리
        ride.setRideDeleted(true);

        // 3. save() 호출해서 업데이트 반영
        return rideRepository.save(ride);
    }
    	 
    
    // 어트랙션 등록 시 코드 중복 여부 체크
    public boolean isDuplicateRideCode(String rideCode) throws Exception {
    	return rideRepository.existsById(rideCode);  // JPA에서 기본 제공 existsById
    }
    
    // 시설팀 직원 목록
    public List<StaffDTO> getStaffList() {
        // 시설팀 dept_code = 1003
        return staffRepository.findByDeptDTO_DeptCode(1003);
    }
    
    

}

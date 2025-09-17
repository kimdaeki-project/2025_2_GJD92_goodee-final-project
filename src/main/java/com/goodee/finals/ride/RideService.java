package com.goodee.finals.ride;

import java.io.File;
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
public class RideService {

    private final StaffController staffController;

    private final StaffRepository staffRepository;

    private final AttachmentRepository attachmentRepository;

    private final FileService fileService;
	
	@Autowired
	private RideRepository rideRepository;

    RideService(FileService fileService, AttachmentRepository attachmentRepository, StaffRepository staffRepository, StaffController staffController) {
        this.fileService = fileService;
        this.attachmentRepository = attachmentRepository;
        this.staffRepository = staffRepository;
        this.staffController = staffController;
    }
	
	// 어트랙션 전체 조회(리스트)
	public List<RideDTO> getAllRides() throws Exception {
		return rideRepository.findAll();
	}
	
	// 등록/수정
	public RideDTO saveRide(RideDTO rideDTO, MultipartFile attach) throws Exception {	
		AttachmentDTO attachmentDTO = null;

		// 새 파일 저장
	    if (attach != null && !attach.isEmpty()) {
	        String fileName = fileService.saveFile(FileService.RIDE, attach);

	        attachmentDTO = new AttachmentDTO();
	        attachmentDTO.setAttachSize(attach.getSize());
	        attachmentDTO.setOriginName(attach.getOriginalFilename());
	        attachmentDTO.setSavedName(fileName);

	        attachmentRepository.save(attachmentDTO);
	    }

	    // 기존 Ride가 있는지 조회
	    RideDTO existing = rideRepository.findById(rideDTO.getRideCode()).orElse(null);

	    if (existing != null) {
	        // 이미 존재하면 기존 RideAttachment 가져와서 update
	        RideAttachmentDTO rideAttachment = existing.getRideAttachmentDTO();
	        if (rideAttachment == null) {
	            rideAttachment = new RideAttachmentDTO();
	            rideAttachment.setRideDTO(existing);
	        }
	        if (attachmentDTO != null) {
	            rideAttachment.setAttachmentDTO(attachmentDTO);
	        }
	        existing.setRideAttachmentDTO(rideAttachment);
	        return rideRepository.save(existing);
	    } else {
	        // RideAttachment 새로 등록
	        RideAttachmentDTO rideAttachment = new RideAttachmentDTO();
	        rideAttachment.setRideDTO(rideDTO);
	        if (attachmentDTO != null) {
	            rideAttachment.setAttachmentDTO(attachmentDTO);
	        }
	        rideDTO.setRideAttachmentDTO(rideAttachment);
	        return rideRepository.save(rideDTO);
	    }
	}
	
	 // 어트랙션 단일 조회
    public RideDTO getRideById(String rideCode) {
        return rideRepository.findById(rideCode).orElse(null);
    }
    
    // 수정
    public void updateRide(RideDTO rideDTO, MultipartFile attach) throws Exception {
    	RideDTO existing = rideRepository.findById(rideDTO.getRideCode())
                .orElseThrow(() -> new Exception("수정할 어트랙션이 없습니다."));

        // null 값이 아닌 것만 업데이트
        if (rideDTO.getRideName() != null) existing.setRideName(rideDTO.getRideName());
        if (rideDTO.getRideType() != null) existing.setRideType(rideDTO.getRideType());
        if (rideDTO.getRideCapacity() != null) existing.setRideCapacity(rideDTO.getRideCapacity());
        if (rideDTO.getRideDuration() != null) existing.setRideDuration(rideDTO.getRideDuration());
        if (rideDTO.getRideInfo() != null) existing.setRideInfo(rideDTO.getRideInfo());
        if (rideDTO.getRideDate() != null) existing.setRideDate(rideDTO.getRideDate());
        if (rideDTO.getRideState() != null) existing.setRideState(rideDTO.getRideState());
        if (rideDTO.getRideRule() != null) existing.setRideRule(rideDTO.getRideRule());
        if (rideDTO.getStaffDTO() != null) {
            existing.setStaffDTO(rideDTO.getStaffDTO());
        }
        
        // === 첨부파일 교체 ===
        if (attach != null && !attach.isEmpty()) {
            // 기존 첨부파일 있으면 삭제
            if (existing.getRideAttachmentDTO() != null) {
                AttachmentDTO oldAttach = existing.getRideAttachmentDTO().getAttachmentDTO();
                // attachmentRepository.delete(oldAttach);   // DB 삭제
                // new File("/upload/path/" + oldAttach.getSavedName()).delete(); // 실제 파일 삭제
            }

            // 새 첨부파일 저장
            String fileName = attach.getOriginalFilename();
            String saveName = System.currentTimeMillis() + "_" + fileName;
            String savePath = "/upload/path/" + saveName;
            attach.transferTo(new File(savePath));

            AttachmentDTO newAttach = new AttachmentDTO();
            newAttach.setOriginName(fileName);
            newAttach.setSavedName(saveName);
            newAttach.setAttachSize(attach.getSize());

            RideAttachmentDTO rideAttachment = new RideAttachmentDTO();
            rideAttachment.setRideDTO(existing);
            rideAttachment.setAttachmentDTO(newAttach);

            existing.setRideAttachmentDTO(rideAttachment);
        }

        rideRepository.save(existing);
    }
    
	
	// 삭제
    @Transactional
	public void deleteRide(String rideCode) throws Exception {
		// 1. 어트랙션 조회
		RideDTO rideDTO = rideRepository.findById(rideCode).orElseThrow();
		
		// 2. rideAttachment 가져오기
//		RideAttachmentDTO rideAttachmentDTO = ride.
		
	}

}

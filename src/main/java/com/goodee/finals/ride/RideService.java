package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.RideAttachmentDTO;
import com.goodee.finals.common.file.FileService;

import jakarta.transaction.Transactional;

@Service
public class RideService {

    private final AttachmentRepository attachmentRepository;

    private final FileService fileService;
	
	@Autowired
	private RideRepository rideRepository;

    RideService(FileService fileService, AttachmentRepository attachmentRepository) {
        this.fileService = fileService;
        this.attachmentRepository = attachmentRepository;
    }
	
	// 어트랙션 전체 조회(리스트)
	public List<RideDTO> getAllRides() throws Exception {
		return rideRepository.findAll();
	}
	
	// 등록/수정
	public RideDTO saveRide(RideDTO rideDTO, MultipartFile attach) throws Exception {
		
//		String fileName = null;
//		AttachmentDTO attachmentDTO = new AttachmentDTO();
//		
//		if (attach != null && attach.getSize() > 0) {
//			try {
//				fileName = fileService.saveFile(FileService.RIDE, attach);
//				
//				attachmentDTO.setAttachSize(attach.getSize());
//				attachmentDTO.setOriginName(attach.getOriginalFilename());
//				attachmentDTO.setSavedName(fileName);
//				
//				attachmentRepository.save(attachmentDTO);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} 
//		
//		RideAttachmentDTO rideAttachmentDTO = new RideAttachmentDTO();
//		rideAttachmentDTO.setRideDTO(rideDTO);
//		rideAttachmentDTO.setAttachmentDTO(attachmentDTO);
//		
//		rideDTO.setRideAttachmentDTO(rideAttachmentDTO);
//		RideDTO result = rideRepository.save(rideDTO);
//		
//		return rideRepository.save(rideDTO);
//	}
		
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
    
	
	// 삭제
    @Transactional
	public void deleteRide(String rideCode) throws Exception {
		// 1. 어트랙션 조회
		RideDTO rideDTO = rideRepository.findById(rideCode).orElseThrow();
		
		// 2. rideAttachment 가져오기
//		RideAttachmentDTO rideAttachmentDTO = ride.
		
	}

}

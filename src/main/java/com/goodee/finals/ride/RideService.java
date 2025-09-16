package com.goodee.finals.ride;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.RideAttachmentDTO;
import com.goodee.finals.common.file.FileService;

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
		} else { 
			attachmentDTO.setAttachSize(0L);
			attachmentDTO.setOriginName("default.png");
			attachmentDTO.setSavedName("default.png");
			
			attachmentRepository.save(attachmentDTO);
		}
		
		RideAttachmentDTO rideAttachmentDTO = new RideAttachmentDTO();
		rideAttachmentDTO.setRideDTO(rideDTO);
		rideAttachmentDTO.setAttachmentDTO(attachmentDTO);
		
		rideDTO.setRideAttachmentDTO(rideAttachmentDTO);
		RideDTO result = rideRepository.save(rideDTO);
		
		return rideRepository.save(rideDTO);
	}
	
	 // 어트랙션 단일 조회
    public RideDTO getRideById(Integer rideCode) {
        return rideRepository.findById(rideCode).orElse(null);
    }
    
    // 수정
    
	
	// 삭제
	public void deleteRide(Integer rideCode) throws Exception {
		rideRepository.deleteById(rideCode);
	}

}

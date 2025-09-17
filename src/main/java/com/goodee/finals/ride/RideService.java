package com.goodee.finals.ride;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${goodee.file.upload.base-directory}")
    private String uploadPath;   // 실제 저장 경로 (C:/goodee/upload/)
	
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
	
	
    // 어트랙션 단일 조회
    public RideDTO getRideById(String rideCode) throws Exception {
        return rideRepository.findById(rideCode).orElse(null);
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

    
    // 수정
    public void updateRide(RideDTO rideDTO, MultipartFile attach) throws Exception {
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
        
        
     // 파일이 넘어왔을 때만 교체 처리
        if (attach != null && !attach.isEmpty()) {
            // 기존 첨부파일 있으면 삭제
            if (origin.getRideAttachmentDTO() != null) {
                AttachmentDTO oldFile = origin.getRideAttachmentDTO().getAttachmentDTO();

                // 물리 파일 삭제
                Path oldPath = Paths.get(uploadPath, oldFile.getSavedName());
                Files.deleteIfExists(oldPath);

                // 관계 해제
                origin.setRideAttachmentDTO(null);

                // DB 삭제
                attachmentRepository.delete(oldFile);
            }

            // 새 파일 저장
            String saveName = UUID.randomUUID().toString() + "_" + attach.getOriginalFilename();
            Path path = Paths.get(uploadPath, saveName);
            Files.copy(attach.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            AttachmentDTO newFile = new AttachmentDTO();
            newFile.setOriginName(attach.getOriginalFilename());
            newFile.setSavedName(saveName);
            newFile.setAttachSize(attach.getSize());
            AttachmentDTO savedNewFile = attachmentRepository.save(newFile);

            RideAttachmentDTO newRA = new RideAttachmentDTO();
            newRA.setRideDTO(origin);
            newRA.setAttachmentDTO(savedNewFile);

            origin.setRideAttachmentDTO(newRA);
        }

        // ride 자체 저장
        rideRepository.save(origin);
    }
    
	
	// 삭제
    @Transactional
	public void deleteRide(String rideCode) throws Exception {
    	 RideDTO ride = rideRepository.findById(rideCode)
                 .orElseThrow(() -> new RuntimeException("어트랙션이 존재하지 않습니다."));

         if (ride.getRideAttachmentDTO() != null) {
             AttachmentDTO file = ride.getRideAttachmentDTO().getAttachmentDTO();

             Path path = Paths.get(uploadPath, file.getSavedName());
             Files.deleteIfExists(path);

             attachmentRepository.delete(file);
         }

         rideRepository.delete(ride);
     }

}

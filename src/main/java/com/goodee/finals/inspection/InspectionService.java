package com.goodee.finals.inspection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.InspectionAttachmentDTO;
import com.goodee.finals.common.file.FileService;
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
    private FileService fileService;
	
	// 어트랙션 점검 리스트 조회
	public Page<InspectionDTO> list(Pageable pageable, String keyword) throws Exception {
		Page<InspectionDTO> result = inspectionRepository.list(keyword, pageable);
		
		return result;
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
		
		inspectionDTO.setInspectionAttachmentDTOs(inspectionAttachmentDTO);
		InspectionDTO result = inspectionRepository.save(inspectionDTO);
		
		if (result != null) return true;
		else return false;
		
	}
	
	
	
	
	
	

}

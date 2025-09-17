package com.goodee.finals.lost;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.LostAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LostService {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private LostRepository lostRepository;
	
	public boolean write(LostDTO lostDTO, MultipartFile attach) {
		StaffDTO staffDTO = new StaffDTO();
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		staffDTO.setStaffCode(staffCode);
		
		lostDTO.setStaffDTO(staffDTO);
		
		String fileName = null;
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		
		if (attach != null && attach.getSize() > 0) {
			try {
				fileName = fileService.saveFile(FileService.LOST, attach);
				
				log.info("{}", attach);
				attachmentDTO.setAttachSize(attach.getSize());
				log.info("{}", attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				log.info("{}", attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				log.info("{}", fileName);
				
				attachmentRepository.save(attachmentDTO);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			attachmentDTO.setAttachSize(0L);
			attachmentDTO.setOriginName("default.png");
			attachmentDTO.setSavedName("default.png");
			
			attachmentRepository.save(attachmentDTO);
		}
		
		LostAttachmentDTO lostAttachmentDTO = new LostAttachmentDTO();
		lostAttachmentDTO.setLostDTO(lostDTO);
		lostAttachmentDTO.setAttachmentDTO(attachmentDTO);
		
		lostDTO.setLostAttachmentDTO(lostAttachmentDTO);
		LostDTO result = lostRepository.save(lostDTO);
		
		if (result != null) return true;
		else return false;
	}
	
}

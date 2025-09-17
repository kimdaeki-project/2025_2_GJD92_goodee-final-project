package com.goodee.finals.lost;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<LostDTO> getLostSearchList(String search, Pageable pageable) {
		return lostRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalLost() {
		return lostRepository.count();
	}
	
	public LostDTO getLost(Long lostNum) {
		return lostRepository.findById(lostNum).orElseThrow();
	}
	
	public LostDTO write(LostDTO lostDTO, MultipartFile attach) {
		StaffDTO staffDTO = new StaffDTO();
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		staffDTO.setStaffCode(staffCode);
		
		lostDTO.setStaffDTO(staffDTO);
		
		String fileName = null;
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		
		if (attach != null && attach.getSize() > 0) {
			try {
				fileName = fileService.saveFile(FileService.LOST, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
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
		
		if (result != null) return lostDTO;
		else return null;
	}
	
//	public boolean updateLost(LostDTO lostDTO, MultipartFile attach) {
//		lostDTO = setLostUpdate(lostDTO);
//		
//		
//	}
//	
//	private LostDTO setLostUpdate(LostDTO after) {
//		LostDTO before = lostRepository.findById(after.getLostNum()).orElseThrow();
//		
//	}
	
}

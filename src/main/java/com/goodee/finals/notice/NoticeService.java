package com.goodee.finals.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.NoticeAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
//	@Autowired
//	private NoticeAttachmentRepository noticeAttachmentRepository;
	
	@Autowired
	private FileService fileService;

	@Transactional
	public NoticeDTO write(NoticeDTO noticeDTO, MultipartFile[] files) {
		String fileName = null;
		List<AttachmentDTO> attachmentDTOs = new ArrayList<>();
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (file != null && file.getSize() > 0) {
					try {
						AttachmentDTO attachmentDTO = new AttachmentDTO();
						fileName = fileService.saveFile(FileService.NOTICE, file);
						attachmentDTO.setAttachSize(file.getSize());
						attachmentDTO.setOriginName(file.getOriginalFilename());
						attachmentDTO.setSavedName(fileName);
						attachmentRepository.save(attachmentDTO);
						attachmentDTOs.add(attachmentDTO);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		noticeDTO.setStaffDTO(staffDTO.get());
		noticeDTO.setNoticeAttachmentDTOs(new ArrayList<NoticeAttachmentDTO>());
		
		for (AttachmentDTO attachmentDTO : attachmentDTOs) {
			NoticeAttachmentDTO noticeAttachmentDTO = new NoticeAttachmentDTO();
			noticeAttachmentDTO.setNoticeDTO(noticeDTO);
			noticeAttachmentDTO.setAttachmentDTO(attachmentDTO);
			noticeDTO.getNoticeAttachmentDTOs().add(noticeAttachmentDTO);
		}
		
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

	public Page<NoticeDTO> list(Pageable pageable, String keyword) {
		// Page<NoticeDTO> result = noticeRepository.findByNoticeTitleContainingOrStaffDTOStaffNameContaining(keyword, keyword, pageable);
		Page<NoticeDTO> result = noticeRepository.list(keyword, pageable);
		return result;
	}

	public List<NoticeDTO> pinned(String keyword) {
		List<NoticeDTO> result = noticeRepository.pinned(keyword);
		return result;
	}
	
	public NoticeDTO detail(NoticeDTO noticeDTO) {
		Long upOneHit = noticeDTO.getNoticeHits() + 1L;
		noticeDTO.setNoticeHits(upOneHit);
		NoticeDTO upOneHitResult = noticeRepository.save(noticeDTO);
		Optional<NoticeDTO> result = null;
		if (upOneHitResult != null) {			
			result = noticeRepository.findById(noticeDTO.getNoticeNum());
		}
		return result.get();
	}

	public NoticeDTO edit(NoticeDTO noticeDTO, MultipartFile[] files, List<Long> deleteFiles) {
		
		if (deleteFiles.size() > 0 && deleteFiles != null) {
			for (Long attachNum : deleteFiles) {
//				noticeAttachmentRepository.deleteById(attachNum);
				attachmentRepository.deleteById(attachNum);
			}
		}
		
		String fileName = null;
		List<AttachmentDTO> attachmentDTOs = new ArrayList<>();
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (file != null && file.getSize() > 0) {
					try {
						AttachmentDTO attachmentDTO = new AttachmentDTO();
						fileName = fileService.saveFile(FileService.NOTICE, file);
						attachmentDTO.setAttachSize(file.getSize());
						attachmentDTO.setOriginName(file.getOriginalFilename());
						attachmentDTO.setSavedName(fileName);
						attachmentRepository.save(attachmentDTO);
						attachmentDTOs.add(attachmentDTO);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		noticeDTO.setStaffDTO(staffDTO.get());
		noticeDTO.setNoticeAttachmentDTOs(new ArrayList<NoticeAttachmentDTO>());
		
		for (AttachmentDTO attachmentDTO : attachmentDTOs) {
			NoticeAttachmentDTO noticeAttachmentDTO = new NoticeAttachmentDTO();
			noticeAttachmentDTO.setNoticeDTO(noticeDTO);
			noticeAttachmentDTO.setAttachmentDTO(attachmentDTO);
			noticeDTO.getNoticeAttachmentDTOs().add(noticeAttachmentDTO);
		}
		
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

	public NoticeDTO delete(NoticeDTO noticeDTO) {
		noticeDTO.setNoticeDelete(true);
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

	public AttachmentDTO download(AttachmentDTO attachmentDTO) {
		Optional<AttachmentDTO> result = attachmentRepository.findById(attachmentDTO.getAttachNum());
		return result.get();
	}


}

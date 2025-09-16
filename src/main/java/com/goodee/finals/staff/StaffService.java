package com.goodee.finals.staff;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.StaffAttachmentDTO;
import com.goodee.finals.common.file.FileService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class StaffService implements UserDetailsService {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FileService fileService;
	
	public StaffDTO getStaff(Integer staffCode) {
		return staffRepository.findById(staffCode).orElseThrow();
	}
	
	public Page<StaffDTO> getStaffSearchList(String search, Pageable pageable) {
		return staffRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalStaff() {
		return staffRepository.count();
	}
	
	public boolean registStaff(StaffDTO staffDTO, MultipartFile attach) {
		staffDTO = setStaffDefault(staffDTO);
		
		String fileName = null;
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		
		if (attach != null && attach.getSize() > 0) {
			try {
				fileName = fileService.saveFile(FileService.STAFF, attach);
				
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
		
		StaffAttachmentDTO staffAttachmentDTO = new StaffAttachmentDTO();
		staffAttachmentDTO.setStaffDTO(staffDTO);
		staffAttachmentDTO.setAttachmentDTO(attachmentDTO);
		
		staffDTO.setStaffAttachmentDTO(staffAttachmentDTO);
		StaffDTO result = staffRepository.save(staffDTO);
		
		if (result != null) return true;
		else return false;
	}
	
	public boolean updateStaff(StaffDTO staffDTO, MultipartFile attach) {
		staffDTO = setStaffUpdate(staffDTO);
		
		if (attach != null && attach.getSize() > 0) {
			StaffDTO before = staffRepository.findById(staffDTO.getStaffCode()).orElseThrow();
			Long beforeAttachKey = before.getStaffAttachmentDTO().getAttachmentDTO().getAttachNum();
			before.setStaffAttachmentDTO(null);
			staffRepository.saveAndFlush(before);
			attachmentRepository.deleteById(beforeAttachKey);
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			
			try {
				String fileName = fileService.saveFile(FileService.STAFF, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
				
				StaffAttachmentDTO staffAttachmentDTO = new StaffAttachmentDTO();
				staffAttachmentDTO.setStaffDTO(staffDTO);
				staffAttachmentDTO.setAttachmentDTO(attachmentDTO);
				
				staffDTO.setStaffAttachmentDTO(staffAttachmentDTO);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		StaffDTO result = staffRepository.saveAndFlush(staffDTO);
		
		// TODO 기존 첨부 이미지 실제 파일 삭제 로직 추가
		
		if (result != null) return true;
		else return false;
	}
	
	private StaffDTO setStaffDefault(StaffDTO staffDTO) {
		staffDTO.setDeptDTO(deptRepository.findById(staffDTO.getInputDeptCode()).orElseThrow());
		staffDTO.setJobDTO(jobRepository.findById(staffDTO.getInputJobCode()).orElseThrow());
		
		int year = LocalDate.now().getYear();
		Integer lastStaffCode = staffRepository.findLastStaffCode();
		
		if (lastStaffCode == null || (lastStaffCode / 10000) != year) {
			staffDTO.setStaffCode((year * 10000) + 1);
		} else {
			staffDTO.setStaffCode(lastStaffCode + 1);
		}
		
		staffDTO.setStaffPw(passwordEncoder.encode("0000"));
		staffDTO.setStaffUsedLeave(0);
		staffDTO.setStaffRemainLeave(0);
		
		return staffDTO;
	}
	
	private StaffDTO setStaffUpdate(StaffDTO after) {
		after.setDeptDTO(deptRepository.findById(after.getInputDeptCode()).orElseThrow());
		after.setJobDTO(jobRepository.findById(after.getInputJobCode()).orElseThrow());
		
		StaffDTO before = staffRepository.findById(after.getStaffCode()).orElseThrow();
		
		after.setStaffLocked(before.getStaffLocked());
		after.setStaffEnabled(before.getStaffEnabled());
		
		after.setStaffRemainLeave(before.getStaffRemainLeave());
		after.setStaffUsedLeave(before.getStaffUsedLeave());
		
		after.setStaffPw(before.getStaffPw());
		
		return after;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Integer staffCode;
		
		try {
			staffCode = Integer.parseInt(username);
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("올바른 사원번호를 입력해주세요.");
		}
		
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		return staffDTO;
	}

	

	

	

	

	

	

}

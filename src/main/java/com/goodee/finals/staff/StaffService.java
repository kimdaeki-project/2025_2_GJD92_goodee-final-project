package com.goodee.finals.staff;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
	
	public List<StaffDTO> getStaffAll() {
		return staffRepository.findAll();
	}
	
	public Page<StaffDTO> getStaffSearchList(String search, Pageable pageable) {
		return staffRepository.findAllBySearch(search, pageable);
	}
	
	public Page<StaffDTO> getStaffQuitSearchList(String search, Pageable pageable) {
		return staffRepository.findAllQuitBySearch(search, pageable);
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
		setStaffUpdate(staffDTO);
		
		if (attach != null && attach.getSize() > 0) {
			changeStaffAttach(staffDTO, attach);
		}
		
		StaffDTO result = staffRepository.saveAndFlush(staffDTO);
		
		if (result != null) return true;
		else return false;
	}
	
	public boolean updateStaffFromInfo(StaffDTO staffDTO, MultipartFile attach) {
		staffDTO = setStaffUpdateFromInfo(staffDTO);
		
		if (attach != null && attach.getSize() > 0) {
			changeStaffAttach(staffDTO, attach);
		}
		
		StaffDTO result = staffRepository.saveAndFlush(staffDTO);
		
		if (result != null) return true;
		else return false;
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

	public void lockStaff(Integer staffCode) {
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		staffDTO.setStaffLocked(false);
		
		staffRepository.saveAndFlush(staffDTO);
	}

	public boolean unlockStaff(Integer staffCode) {
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		if (staffDTO.getStaffLocked()) {
			return false;
		} else {
			staffDTO.setStaffLocked(true);
			staffRepository.saveAndFlush(staffDTO);
			
			return true;
		}
	}
	
	public boolean disableStaff(Integer staffCode) {
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		if (!staffDTO.getStaffEnabled()) {
			return false;
		} else {
			staffDTO.setStaffEnabled(false);
			staffDTO.setStaffFireDate(LocalDate.now());
			staffRepository.saveAndFlush(staffDTO);
			
			return true;
		}
	}
	
	public boolean enableStaff(Integer staffCode) {
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		if (staffDTO.getStaffEnabled()) {
			return false;
		} else {
			staffDTO.setStaffEnabled(true);
			staffDTO.setStaffFireDate(null);
			staffRepository.saveAndFlush(staffDTO);
			
			return true;
		}
	}

	public int updateStaffPassword(PasswordDTO passwordDTO) {
		StaffDTO staffDTO = staffRepository.findById(passwordDTO.getStaffCode()).orElseThrow();
		
		// TODO 비밀번호 유효성 검사
		if (passwordDTO.getNewPw().equals("0000")) return 400;
		if (!passwordEncoder.matches(passwordDTO.getOldPw(), staffDTO.getStaffPw())) return 401;
		if (!passwordDTO.getNewPw().equals(passwordDTO.getNewPwChk())) return 402;
		if (passwordEncoder.matches(passwordDTO.getNewPw(), staffDTO.getStaffPw())) return 403;
		
		staffDTO.setStaffPw(passwordEncoder.encode(passwordDTO.getNewPw()));
		staffRepository.saveAndFlush(staffDTO);
		
		return 200;
	}
	
	public boolean updateStaffLeave(LeaveDTO leaveDTO) {
		StaffDTO staffDTO = staffRepository.findById(leaveDTO.getStaffCode()).orElseThrow();
		
		staffDTO.setStaffRemainLeave(leaveDTO.getStaffRemainLeave());
		staffDTO.setStaffUsedLeave(leaveDTO.getStaffUsedLeave());
		
		StaffDTO result = staffRepository.saveAndFlush(staffDTO);
		
		if (result != null) return true;
		else return false;
	}
	
	
	
	
	// Inner Methods
	
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
	
	private void setStaffUpdate(StaffDTO after) {
		after.setDeptDTO(deptRepository.findById(after.getInputDeptCode()).orElseThrow());
		after.setJobDTO(jobRepository.findById(after.getInputJobCode()).orElseThrow());
		
		StaffDTO before = staffRepository.findById(after.getStaffCode()).orElseThrow();
		
		after.setStaffLocked(before.getStaffLocked());
		after.setStaffEnabled(before.getStaffEnabled());
		
		after.setStaffRemainLeave(before.getStaffRemainLeave());
		after.setStaffUsedLeave(before.getStaffUsedLeave());
		
		after.setStaffPw(before.getStaffPw());
	}
	
	private StaffDTO setStaffUpdateFromInfo(StaffDTO after) {
		StaffDTO before = staffRepository.findById(after.getStaffCode()).orElseThrow();
		
		before.setStaffEmail(after.getStaffEmail());
		before.setStaffPhone(after.getStaffPhone());
		before.setStaffPostcode(after.getStaffPostcode());
		before.setStaffAddress(after.getStaffAddress());
		before.setStaffAddressDetail(after.getStaffAddressDetail());
		
		return before;
	}
	
	private void changeStaffAttach(StaffDTO staffDTO, MultipartFile attach) {
		StaffDTO before = staffRepository.findById(staffDTO.getStaffCode()).orElseThrow();
		AttachmentDTO beforeAttach = before.getStaffAttachmentDTO().getAttachmentDTO();
		attachmentRepository.deleteById(beforeAttach.getAttachNum());
		
		fileService.fileDelete(FileService.STAFF, beforeAttach.getSavedName());
		
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
	
}

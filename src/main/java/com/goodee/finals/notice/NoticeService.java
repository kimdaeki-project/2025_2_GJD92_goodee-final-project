package com.goodee.finals.notice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private StaffRepository staffRepository;

	public NoticeDTO write(NoticeDTO noticeDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		noticeDTO.setStaffDTO(staffDTO.get());
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

	public Page<NoticeDTO> list(Pageable pageable) {
		Page<NoticeDTO> result = noticeRepository.findAll(pageable);
		return result;
	}

	public NoticeDTO detail(NoticeDTO noticeDTO) {
		Optional<NoticeDTO> result = noticeRepository.findById(noticeDTO.getNoticeNum());
		return result.get();
	}

}

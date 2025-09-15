package com.goodee.finals.notice;

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

	public Page<NoticeDTO> list(Pageable pageable, String keyword) {
		// Page<NoticeDTO> result = noticeRepository.findByNoticeTitleContainingOrStaffDTOStaffNameContaining(keyword, keyword, pageable);
		Page<NoticeDTO> result = noticeRepository.list(keyword, pageable);
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

	public NoticeDTO edit(NoticeDTO noticeDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		noticeDTO.setStaffDTO(staffDTO.get());
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

	public NoticeDTO delete(NoticeDTO noticeDTO) {
		noticeDTO.setNoticeDelete(true);
		NoticeDTO result = noticeRepository.save(noticeDTO);
		return result;
	}

}

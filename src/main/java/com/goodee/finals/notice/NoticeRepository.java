package com.goodee.finals.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeDTO, Long> {

	// Page<NoticeDTO> findByNoticeTitleContainingOrStaffDTOStaffNameContaining(String titleKeyword, String nameKeyword, Pageable pageable);
	
	@Query("SELECT n FROM NoticeDTO n " +
	       "WHERE n.noticeDelete = false AND " +
	       "(n.noticeTitle LIKE %:keyword% OR n.staffDTO.staffName LIKE %:keyword%)")
	Page<NoticeDTO> list(@Param("keyword") String keyword, Pageable pageable);

}

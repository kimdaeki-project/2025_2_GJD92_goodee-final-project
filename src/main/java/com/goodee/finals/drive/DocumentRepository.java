package com.goodee.finals.drive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentDTO, Long>{
	public DocumentDTO findByattachmentDTO_AttachNum(Long attachNum);
	
	@Query("SELECT d FROM DocumentDTO d " +
	       "WHERE d.driveDTO.driveNum = :driveNum " +
	       "AND d.docStatus = 'ACTIVE' " +
	       "AND d.jobDTO.jobCode >= :jobCode " +  // ← 여기서 필터링
	       "AND (d.attachmentDTO.originName LIKE %:keyword% " +
	       "OR d.staffDTO.staffName LIKE %:keyword%)")
	Page<DocumentDTO> findByDriveAndKeyword(@Param("driveNum") Long driveNum,
	                                        @Param("keyword") String keyword,
	                                        @Param("jobCode") Integer jobCode,
	                                        Pageable pageable);

}

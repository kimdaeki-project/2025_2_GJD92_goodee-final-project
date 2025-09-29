package com.goodee.finals.common.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentDTO, Long> {

	@NativeQuery(value = "DELETE FROM attachment WHERE attach_num = :attachNum")
	void deleteAttach(Long attachNum);
	
}

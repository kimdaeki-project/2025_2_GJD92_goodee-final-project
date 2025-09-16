package com.goodee.finals.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodee.finals.common.attachment.NoticeAttachmentDTO;

@Repository
public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachmentDTO, Long> {

}

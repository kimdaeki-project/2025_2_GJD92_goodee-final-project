package com.goodee.finals.common.attachment;

import com.goodee.finals.notice.NoticeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "notice_attach")
public class NoticeAttachmentDTO {
	@Id
	@ManyToOne
	@JoinColumn(name = "noticeNum", insertable = false, updatable = false)
	private NoticeDTO noticeDTO;
	@Id
	@ManyToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
}

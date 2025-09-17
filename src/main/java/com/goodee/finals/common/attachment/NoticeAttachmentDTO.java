package com.goodee.finals.common.attachment;

import com.goodee.finals.notice.NoticeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "notice_attach")
@IdClass(NoticeAttachmentDTO.PK.class)
public class NoticeAttachmentDTO {
	@Id
	@ManyToOne
	@JoinColumn(name = "noticeNum")
	private NoticeDTO noticeDTO;
	@Id
	@ManyToOne
	@JoinColumn(name = "attachNum")
	private AttachmentDTO attachmentDTO;
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Long noticeDTO;
		private Long attachmentDTO;
	}
}

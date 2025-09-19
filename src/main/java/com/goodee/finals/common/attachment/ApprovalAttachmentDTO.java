package com.goodee.finals.common.attachment;

import com.goodee.finals.approval.ApprovalDataDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "approval_attach")
@IdClass(ApprovalAttachmentDTO.PK.class)
public class ApprovalAttachmentDTO {

	@Id
	@OneToOne
	@JoinColumn(name = "aprvNum", insertable = false, updatable = false)
	private ApprovalDataDTO approvalDataDTO;
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Long approvalDataDTO;
		private Long attachmentDTO;
	}
}

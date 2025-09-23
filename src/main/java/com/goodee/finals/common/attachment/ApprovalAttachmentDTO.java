package com.goodee.finals.common.attachment;

import com.goodee.finals.approval.ApprovalDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name = "aprvCode", insertable = false, updatable = false)
	private ApprovalDTO approvalDTO;
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer approvalDTO;
		private Long attachmentDTO;
	}
}

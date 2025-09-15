package com.goodee.finals.common.attachment;

import com.goodee.finals.drive.DocumentDTO;

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
@Table(name = "document_attach")
@IdClass(DocumentAttachmentDTO.PK.class)
public class DocumentAttachmentDTO {

	@Id
	@OneToOne
	@JoinColumn(name = "docNum", insertable = false, updatable = false)
	private DocumentDTO documentDTO;
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;

	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Long documentDTO;
		private Long attachmentDTO;
	}
	
}

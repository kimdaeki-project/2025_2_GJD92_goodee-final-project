package com.goodee.finals.common.attachment;

import com.goodee.finals.lost.LostDTO;

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
@Table(name = "lost_attach")
@IdClass(LostAttachmentDTO.PK.class)
public class LostAttachmentDTO {

	@Id
	@OneToOne
	@JoinColumn(name = "lostNum", insertable = false, updatable = false)
	private LostDTO lostDTO;
	
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Long lostDTO;
		private Long attachmentDTO;
	}
	
}

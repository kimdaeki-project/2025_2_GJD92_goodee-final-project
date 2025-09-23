package com.goodee.finals.common.attachment;

import com.goodee.finals.inspection.InspectionDTO;

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

// 어트랙션 점검 기록 첨부파일 중간 테이블

@Getter
@Setter
@Entity
@Table(name = "inspection_attach")
@IdClass(InspectionAttachmentDTO.PK.class)
public class InspectionAttachmentDTO {
	
	@Id
	@OneToOne
	@JoinColumn(name = "isptNum", insertable = false, updatable = false)
	private InspectionDTO inspectionDTO;
	
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, nullable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer inspectionDTO;
		private Long attachmentDTO;
	}
	

}

package com.goodee.finals.common.attachment;

import com.goodee.finals.fault.FaultDTO;

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

// 어트랙션 고장 신고 첨부파일 중간 테이블

@Getter
@Setter
@Entity
@Table(name = "fault_attach")
@IdClass(FaultAttachmentDTO.class)
public class FaultAttachmentDTO {

	
	@Id
	@OneToOne
	@JoinColumn(name = "faultNum", insertable = false, updatable = false)
	private FaultDTO faultDTO;
	
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer faultDTO;
		private Long attachmentDTO;
	}
	
	
}

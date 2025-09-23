package com.goodee.finals.common.attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.StaffDTO;

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
@Table(name = "staff_attach")
@IdClass(StaffAttachmentDTO.PK.class)
public class StaffAttachmentDTO {

	@Id
	@OneToOne
	@JoinColumn(name = "staffCode", insertable = false, updatable = false)
	@JsonIgnore
	private StaffDTO staffDTO;
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer staffDTO;
		private Long attachmentDTO;
	}
}

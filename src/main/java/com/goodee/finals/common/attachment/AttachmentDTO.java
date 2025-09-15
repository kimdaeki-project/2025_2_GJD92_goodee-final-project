package com.goodee.finals.common.attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attachment")
public class AttachmentDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long attachNum;
	private String originName;
	private String savedName;
	private Long attachSize;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private StaffAttachmentDTO staffAttachmentDTO;
}

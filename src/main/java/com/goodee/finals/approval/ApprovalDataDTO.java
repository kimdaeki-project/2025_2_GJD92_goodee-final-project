package com.goodee.finals.approval;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.common.attachment.ApprovalAttachmentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "approval_data")
public class ApprovalDataDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long aprvNum;
	private String aprvTitle;
	@Lob
	private String aprvContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvExe;
	
	@OneToOne
	@JoinColumn(name = "aprvCode")
	@JsonIgnore
	private ApprovalDTO approvalDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDataDTO", cascade = CascadeType.ALL)
	private ApprovalAttachmentDTO approvalAttachmentDTO;
}

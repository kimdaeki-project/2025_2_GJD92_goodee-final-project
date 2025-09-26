package com.goodee.finals.approval;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodee.finals.common.attachment.ApprovalAttachmentDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "approval")
public class ApprovalDTO {
	@Id
	private Integer aprvCode;
	private Integer aprvType;
	private String aprvTitle;
	@Lob
	private String aprvContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvExe;
	private Integer aprvState;
	private Integer aprvTotal;
	private Integer aprvCrnt;

	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("apvrSeq DESC")
	private List<ApproverDTO> approverDTOs;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private List<ApprovalAttachmentDTO> approvalAttachmentDTOs;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private VacationDTO vacationDTO;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private OvertimeDTO overtimeDTO;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private EarlyDTO earlyDTO;
}

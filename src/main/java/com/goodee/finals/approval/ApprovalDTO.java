package com.goodee.finals.approval;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvDate;
	private Integer aprvState;

	@OneToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private List<ApproverDTO> approverDTOs;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private VacationDTO vacationDTO;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private OvertimeDTO overtimeDTO;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private EarlyDTO earlyDTO;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "approvalDTO", cascade = CascadeType.ALL)
	private ApprovalDataDTO approvalDataDTO;
}

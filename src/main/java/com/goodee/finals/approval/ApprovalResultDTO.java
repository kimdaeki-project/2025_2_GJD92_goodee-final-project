package com.goodee.finals.approval;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalResultDTO {
	private Integer aprvCode;
	private String aprvTitle;
	private Integer aprvTotal;
	private Integer aprvCrnt;
	private String staffName;
	private String deptDetail;
	private Integer apvrState;
	private Integer aprvState;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvDate;
	private LocalDate aprvExe;
	
	public ApprovalResultDTO(Integer aprvCode, String aprvTitle, Integer aprvTotal, Integer aprvCrnt, String staffName, String deptDetail, Integer aprvState, LocalDate aprvDate, LocalDate aprvExe) {
		this.aprvCode = aprvCode;
		this.aprvTitle = aprvTitle;
		this.aprvTotal = aprvTotal;
		this.aprvCrnt = aprvCrnt;
		this.staffName = staffName;
		this.deptDetail = deptDetail;
		this.aprvState = aprvState;
		this.aprvDate = aprvDate;
		this.aprvExe = aprvExe;
	}
	
}

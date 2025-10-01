package com.goodee.finals.staff;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffVacationDTO {
	private Integer aprvCode;
	private String staffName;
	private String deptDetail;
	private String jobDetail;
	private Long vacNum;
	private Integer vacType;
	private LocalDate vacStart;
	private LocalDate vacEnd;
}

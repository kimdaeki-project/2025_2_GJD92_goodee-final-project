package com.goodee.finals.staff;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffEarlyDTO {
	private Integer aprvCode;
	private String staffName;
	private String deptDetail;
	private String jobDetail;
	private Long earlyNum;
	private Integer earlyType;
	private LocalDateTime earlyDtm;
}

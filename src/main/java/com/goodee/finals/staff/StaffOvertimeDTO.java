package com.goodee.finals.staff;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffOvertimeDTO {
	private Integer aprvCode;
	private String staffName;
	private String deptDetail;
	private String jobDetail;
	private Long overNum;
	private LocalDateTime overStart;
	private LocalDateTime overEnd;
}

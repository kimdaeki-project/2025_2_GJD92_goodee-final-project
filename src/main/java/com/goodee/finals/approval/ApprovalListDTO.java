package com.goodee.finals.approval;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApprovalListDTO {
	private Integer aprvCode;
	private String aprvTitle;
	private Integer aprvTotal;
	private Integer aprvCrnt;
	private String staffName;
	private String deptDetail;
	private Integer apvrState;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate aprvDate;
}

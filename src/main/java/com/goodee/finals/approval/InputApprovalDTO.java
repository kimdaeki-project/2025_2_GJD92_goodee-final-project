package com.goodee.finals.approval;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InputApprovalDTO {
	private String aprvTitle;
	private String aprvContent;
	private LocalDate aprvExe;
	
	private List<String> approver;
	private List<String> receiver;
	private List<String> agreer;
}

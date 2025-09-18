package com.goodee.finals.staff;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LeaveDTO {
	private Integer staffCode;
	private Integer staffUsedLeave;
	private Integer staffRemainLeave;
}

package com.goodee.finals.staff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponseDTO {

	private Integer staffCode;
	private String staffName;
	
	private DeptDTO deptDTO;
	private JobDTO jobDTO;
	
	public StaffResponseDTO(StaffDTO staff) {
		this.staffCode = staff.getStaffCode();
		this.staffName = staff.getStaffName();
		this.deptDTO = staff.getDeptDTO();
		this.jobDTO = staff.getJobDTO();
	}
	
}

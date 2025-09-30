package com.goodee.finals.calendar;

import lombok.Getter;

@Getter
public class CalTypeDTO {

	private Integer calType;
	private String calTypeName;
	
	public CalTypeDTO(Integer calType, String calTypeName) {
		this.calType = calType;
		this.calTypeName = calTypeName;
	}
	
}

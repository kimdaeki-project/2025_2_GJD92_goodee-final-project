package com.goodee.finals.calendar;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "calendar")
public class CalendarDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long calNum;
	@NotBlank(message = "제목은 필수 입니다")
	private Integer calType;
	private String calTypeName;
	private String calTitle;
	private String calPlace;
	private String calContent;
	private Boolean calEnabled;
	private Boolean calIsAllDay;
	@NotBlank
	private LocalDateTime calStart;
	@NotBlank
	private LocalDateTime calEnd;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "deptCode")
	private DeptDTO deptDTO;
	
}

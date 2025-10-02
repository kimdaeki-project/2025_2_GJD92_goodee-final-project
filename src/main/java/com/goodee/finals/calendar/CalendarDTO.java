package com.goodee.finals.calendar;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	private Integer calType;
	private String calTypeName;
	private String calTitle;
	private String calPlace;
	private String calContent;
	private Boolean calEnabled;
	private Boolean calIsAllDay;
	@Column(columnDefinition = "datetime(6)")
	private LocalDateTime calStart;
	@Column(columnDefinition = "datetime(6)")
	private LocalDateTime calEnd;
	private LocalDateTime calReg;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@ManyToOne
	@JoinColumn(name = "deptCode")
	private DeptDTO deptDTO;
	
}

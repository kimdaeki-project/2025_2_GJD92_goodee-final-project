package com.goodee.finals.alert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "alert")
public class AlertDTO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long alertNum;
	private String alertMsg;
	@Column(columnDefinition = "boolean default false")
	private boolean alertCheck;
	@Column(columnDefinition = "boolean default false")
	private boolean alertDelete;
	
	@ManyToOne @JoinColumn(name = "staffCode") @JsonIgnore
	private StaffDTO staffDTO;
	
	@Transient
	private Integer staffCodeToDb;
	@Transient
	private Long alertNumToDelete;
}

package com.goodee.finals.staff;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "job")
public class JobDTO {
	@Id
	private Integer jobCode;
	private String jobName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDTO", cascade = CascadeType.ALL)
	private List<StaffDTO> staffDTOs;
	
}

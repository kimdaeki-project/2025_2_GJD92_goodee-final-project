package com.goodee.finals.staff;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dept")
public class DeptDTO {
	@Id
	private Integer deptCode;
	private String deptName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deptDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<StaffDTO> staffDTOs;
}

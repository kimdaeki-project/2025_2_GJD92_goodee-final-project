package com.goodee.finals.staff;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.drive.DocumentDTO;

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
@Table(name = "job")
public class JobDTO {
	@Id
	private Integer jobCode;
	private String jobName;
	private String jobDetail;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<StaffDTO> staffDTOs;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "jobDTO", cascade = CascadeType.ALL)
	private List<DocumentDTO> documentDTOs;
	
}

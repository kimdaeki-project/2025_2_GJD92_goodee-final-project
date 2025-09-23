package com.goodee.finals.approval;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vacation")
public class VacationDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vacNum;
	private Integer vacType;
	private String vacReason;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate vacStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate vacEnd;
	
	@OneToOne
	@JoinColumn(name = "aprvCode")
	@JsonIgnore
	private ApprovalDTO approvalDTO;
}

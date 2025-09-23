package com.goodee.finals.approval;

import java.time.LocalDateTime;

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
@Table(name = "overtime")
public class OvertimeDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long overNum;
	private String overReason;
	private LocalDateTime overStart;
	private LocalDateTime overEnd;
	
	@OneToOne
	@JoinColumn(name = "aprvCode")
	@JsonIgnore
	private ApprovalDTO approvalDTO;
}

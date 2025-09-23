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
@Table(name = "early")
public class EarlyDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long earlyNum;
	private Integer earlyType;
	private String earlyReason;
	private LocalDateTime earlyDtm;
	
	@OneToOne
	@JoinColumn(name = "aprvCode")
	@JsonIgnore
	private ApprovalDTO approvalDTO;
}

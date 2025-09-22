package com.goodee.finals.approval;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.StaffDTO;

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
@Table(name = "approver")
public class ApproverDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apvrNum;
	private Integer apvrType;
	private Integer apvrSeq;
	private Integer apvrState;
	private Boolean apvrResult;
	private LocalDateTime apvrDtm;
	
	@ManyToOne
	@JoinColumn(name = "aprvCode")
	@JsonIgnore
	private ApprovalDTO approvalDTO;
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
}

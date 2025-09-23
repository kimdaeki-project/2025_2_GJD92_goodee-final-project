package com.goodee.finals.inspection;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodee.finals.common.attachment.InspectionAttachmentDTO;
import com.goodee.finals.common.attachment.RideAttachmentDTO;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

// JPA가 관리하는 엔티티임
// VO 클래스에 반드시 선언해야 DB 테이블과 매핑 가능
@Entity

// DB 테이블과 매핑
// 클래스명과 테이블명이 다를 경우 지정
@Table(name = "inspection")
public class InspectionDTO {
	
	
	@Id  // 기본키(PK), 모든 엔티티에는 반드시 하나 이상의 @Id가 필요함
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT
	private Integer isptNum;
	
	@Column(name = "isptType")
	private Integer isptType;  // 점검유형
	
	@Column(name = "isptResult")
	private Integer isptResult;  // 점검결과
	
	@Column(name = "isptStart", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate isptStart;  // 점검 시작일
	
	@Column(name = "isptEnd", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate isptEnd;  // 점검 시작일
	
	@Column(columnDefinition = "boolean default false")
	private boolean isptDelete;  // 점검 기록 삭제 여부
	
	// 하나의 어트랙션에 여러 inspection(점검) 가능
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rideCode", nullable = false)
	private RideDTO rideDTO;  // 어트랙션 번호
	
	// 사원이 여러개의 어트랙션 담당 가능
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staffCode", nullable = false)
	private StaffDTO staffDTO;  // 사원번호
	
	// 점검기록 하나에 첨부파일 하나
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "inspectionDTO", cascade = CascadeType.ALL)
	private InspectionAttachmentDTO inspectionAttachmentDTO;
	
	

}

package com.goodee.finals.fault;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

//JPA가 관리하는 엔티티임
//VO 클래스에 반드시 선언해야 DB 테이블과 매핑 가능
@Entity

//DB 테이블과 매핑
//클래스명과 테이블명이 다를 경우 지정
@Table(name = "fault")
public class FaultDTO {
	
	@Id // 기본키(PK), 모든 엔터티에는 반드시 하나 이상의 @ID가 필요
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer faultNum;  // 고장 신고 번호
	
	@Column(name = "faultTitle")
	private String faultTitle;  // 고장 신고 제목
	
	@Column(name = "faultContent")
	private String faultContent;  // 고장 신고 내용
	
	@Column(name = "faultDate", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate faultDate;  // 고장 신고 날짜
	
	@Column(name = "faultState")
	private Integer faultState;  // 고장 신고 상태
	
	@Column(columnDefinition = "boolean default false")
	private boolean faultDelete;  // 고장 신고 삭제 여부
	
	
	// 하나의 어트랙션에 여러 fault(고장신고) 가능
	@ManyToOne(fetch =FetchType.LAZY )
	@JoinColumn(name = "rideCode", nullable = false)
	private RideDTO rideDTO;
	
	
	// 사원이 여러개의 어트랙션 고장 신고 담당 가능
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staffCode", nullable = false)
	private StaffDTO staffDTO; 
	
	
	// 어트랙션 고장 신고 하나에 첨부파일 하나
	/* private FaultAttachmentDAO faultAttachmentDTO; */
	
	
	
	

}

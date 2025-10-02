package com.goodee.finals.ride;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.RideAttachmentDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "ride")
public class RideDTO {
	
	@Id  // 기본키(PK), 모든 엔티티에는 반드시 하나 이상의 @Id가 필요함
	@Column(name = "rideCode")
	@NotBlank(message = "어트랙션 번호를 입력해주세요.")
	private String rideCode;  // 어트랙션 번호 (PK)
	
	@Column(name = "rideName", nullable = false)
	@NotBlank(message = "어트랙션 이름을 입력해주세요.")
	private String rideName;  // 어트랙션 이름
	
	@Column(name ="rideType", nullable = false, length = 255)
	@NotBlank(message = "어트랙션 기종을 입력해주세요.")
	private String rideType;  // 어트랙션 기종
	
	@Column(name ="rideCapacity", nullable = false)
	@NotBlank(message = "탑승인원을 입력해주세요.")
	private String  rideCapacity;  // 탑승인원
	
	@Column(name = "rideDuration", nullable = false)
	@NotBlank(message = "운행시간을 입력해주세요.")
	private String  rideDuration;  // 운행시간
	
	@Column(name = "rideRule", columnDefinition = "LONGTEXT", nullable = false)
	@NotBlank(message = "이용정보를 입력해주세요.")
	private String rideRule;  // 이용정보
	
	@Column(name = "rideInfo", columnDefinition = "LONGTEXT", nullable = false)
	@NotBlank(message = "어트랙션 설명을 입력해주세요.")
	private String rideInfo;  // 어트랙션 설명
	
	@Column(name = "rideDate", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "개장일을 입력해주세요.")
	private LocalDate rideDate;  // 개장일
	
	@Column(name = "rideState", nullable = false)
	@Positive(message = "운행상태을 입력해주세요.")
	private Integer  rideState;  // 운행상태
	
	@Column(columnDefinition = "boolean default false")
	private boolean rideDeleted = false;  // 논리적 삭제
	
	// 사원이 여러개의 어트랙션 담당 가능
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;  // 사원번호
	
	// 어트랙션 하나에 첨부파일 하나
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "rideDTO", cascade = CascadeType.ALL)
	private RideAttachmentDTO rideAttachmentDTO;
	
	
	// 
	@Transient
	@Positive(message = "사원을 입력해주세요.")
	private Integer staffCodeValid;
	 
	
}

package com.goodee.finals.ride;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "ride")
public class RideVO {
	
	@Id
	private Long ride_code;  // 어트랙션 번호
	// 사원번호
	private String ride_name;  // 어트랙션 이름
	private String ride_type;  // 어트랙션 기종
	private Long ride_capacity;  // 탑승인원
	private Long ride_duration;  // 운행시간
	private String ride_rule;  // 이용정보
	private String ride_info;  // 어트랙션 설명
	private LocalDate ride_date;  // 개장일
	private Long ride_state;  // 운행상태
	
	
	
	
}

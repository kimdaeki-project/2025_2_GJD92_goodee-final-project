package com.goodee.finals.lost;

import java.time.LocalDate;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
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
@Entity
@Table(name = "lost")
public class LostDTO {
	
	@Id
	private Long lostNum;
	private String lostName;
	private String lostFinder;
	private String lostFinderPhone;
	private LocalDate lostDate;
	
	// 분실물 작성자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;

	// 분실물 사진파일
	
}

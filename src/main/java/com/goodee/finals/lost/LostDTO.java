package com.goodee.finals.lost;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goodee.finals.common.attachment.LostAttachmentDTO;
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
@Entity
@Table(name = "lost")
public class LostDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lostNum;
	private String lostName;
	private String lostFinder;
	private String lostFinderPhone;
	private String lostNote;
	private LocalDate lostDate = LocalDate.now();
	@Column(columnDefinition = "boolean default false")
	private boolean lostDelete;
	
	// 분실물 작성자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	@JsonIgnoreProperties({"deptDTO", "lostList"}) // 필요한 필드만 제외
	private StaffDTO staffDTO;

	// 분실물 사진파일
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "lostDTO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private LostAttachmentDTO lostAttachmentDTO; 
	
}

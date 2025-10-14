package com.goodee.finals.drive;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "drive")
public class DriveDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long driveNum;
	@NotBlank(message = "드라이브 이름은 필수입니다 1~50자 이하로 입력해주세요.")
	@Size(min = 1, max = 50)
	private String driveName; // 사용하지 않고 있음
	@Column(unique = true)
	private Long driveDefaultNum;
	private Boolean isPersonal;
	private Boolean driveEnabled;
	@CreationTimestamp
	private LocalDateTime driveDate;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	@JsonIgnore
	private StaffDTO staffDTO;
	
	@JsonIgnore
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<DriveShareDTO> driveShareDTOs;
	
	@JsonIgnore
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DocumentDTO> documentDTOs;
	
}

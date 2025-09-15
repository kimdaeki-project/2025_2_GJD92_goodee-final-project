package com.goodee.finals.drive;

import java.time.LocalDate;
import java.util.List;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private String driveName;
	private Boolean isPersonal;
	private LocalDate driveDate;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DriveShareDTO> driveShareDTOs;
	
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DocumentDTO> documentDTOs;
	
}

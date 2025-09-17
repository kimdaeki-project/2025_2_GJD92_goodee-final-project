package com.goodee.finals.drive;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@CreationTimestamp
	private LocalDateTime driveDate;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	@JsonIgnore
	private StaffDTO staffDTO;
	
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DriveShareDTO> driveShareDTOs;
	
	@OneToMany(mappedBy = "driveDTO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DocumentDTO> documentDTOs;
	
}

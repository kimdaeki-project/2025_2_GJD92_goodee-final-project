package com.goodee.finals.drive;

import java.time.LocalDate;

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
@Table(name = "document")
public class DocumentDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long docNum;
	
	private String docName;
	private LocalDate docDate;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	@ManyToOne
	@JoinColumn(name = "driveNum")
	private DriveDTO driveDTO;
	
}

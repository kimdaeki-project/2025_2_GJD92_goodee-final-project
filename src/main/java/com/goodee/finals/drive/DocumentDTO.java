package com.goodee.finals.drive;

import java.time.LocalDate;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class DocumentDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long docNum;
	
	private String docName;
	private LocalDate docDate;
	
	private StaffDTO staffDTO;
	private DriveDTO driveDTO;
	
}

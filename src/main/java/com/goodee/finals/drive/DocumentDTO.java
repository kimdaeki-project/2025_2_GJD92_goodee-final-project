package com.goodee.finals.drive;

import java.time.LocalDate;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.staff.JobDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	private String docStatus; // ACTIVE, DELETE, EXPIRE
	private LocalDate docDate;
	private LocalDate docExpire;
	private String docContentType;
	
	@ManyToOne
	@JoinColumn(name = "staffCode", nullable = false)
	private StaffDTO staffDTO;
	
	@ManyToOne
	@JoinColumn(name = "driveNum", nullable = false)
	private DriveDTO driveDTO;
	
	@ManyToOne
	@JoinColumn(name = "jobCode", nullable = false)
	private JobDTO jobDTO;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "attachNum")
	private AttachmentDTO attachmentDTO; 
	
	
}

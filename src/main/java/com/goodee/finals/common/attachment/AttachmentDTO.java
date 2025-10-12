package com.goodee.finals.common.attachment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.drive.DocumentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attachment")
public class AttachmentDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachNum;
	private String originName;
	private String savedName;
	private Long attachSize;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private StaffAttachmentDTO staffAttachmentDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private StaffSignDTO staffSignDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private LostAttachmentDTO lostAttachmentDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private ProductAttachmentDTO productAttachmentDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private ApprovalAttachmentDTO approvalAttachmentDTO;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private RideAttachmentDTO rideAttachmentDTO;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<NoticeAttachmentDTO> noticeAttachmentDTOs;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private InspectionAttachmentDTO inspectionAttachmentDTO;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "attachmentDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private FaultAttachmentDTO faultAttachmentDTO;
	
	@OneToOne(mappedBy = "attachmentDTO")
	@JsonIgnore
	private DocumentDTO documentDTO;
	
}

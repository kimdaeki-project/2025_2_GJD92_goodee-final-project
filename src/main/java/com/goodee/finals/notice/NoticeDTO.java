package com.goodee.finals.notice;

import java.time.LocalDate;
import java.util.List;

import com.goodee.finals.common.attachment.NoticeAttachmentDTO;
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
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "notice")
@Getter @Setter
public class NoticeDTO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeNum;
	@NotBlank(message = "제목은 필수 입력 항목입니다.")
	private String noticeTitle;
	@Column(columnDefinition = "LONGTEXT")
	@NotBlank(message = "내용은 필수 입력 항목입니다.")
	private String noticeContent;
	private LocalDate noticeDate = LocalDate.now();
	@Column(columnDefinition = "boolean default false")
	private boolean noticePinned;
	private Long noticeHits = 0L;
	@Column(columnDefinition = "boolean default false")
	private boolean noticeTmp;
	@Column(columnDefinition = "boolean default false")
	private boolean noticeDelete;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "noticeDTO", cascade = CascadeType.ALL)
	private List<NoticeAttachmentDTO> noticeAttachmentDTOs;
}

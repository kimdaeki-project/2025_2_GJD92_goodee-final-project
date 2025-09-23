package com.goodee.finals.staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.approval.ApprovalDTO;
import com.goodee.finals.approval.ApproverDTO;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.StaffAttachmentDTO;
import com.goodee.finals.messenger.ChatUserDTO;
import com.goodee.finals.notice.NoticeDTO;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "staffCode")
@Entity
@Table(name = "staff")
public class StaffDTO implements UserDetails {
	@Id
	private Integer staffCode;
	@ManyToOne
	@JoinColumn(name = "deptCode")
	private DeptDTO deptDTO;
	@ManyToOne
	@JoinColumn(name = "jobCode")
	private JobDTO jobDTO;
	private String staffPw;
	
	private String staffName;
	private Integer staffGender;
	private String staffEmail;
	private String staffPhone;
	private Integer staffPostcode;
	private String staffAddress;
	private String staffAddressDetail;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate staffHireDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate staffFireDate;
	
	private Integer staffUsedLeave;
	private Integer staffRemainLeave;
	
	@OneToMany(mappedBy = "staffDTO")
	private List<NoticeDTO> notices;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "staffDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ChatUserDTO> chatUserDTOs;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "staffDTO", cascade = CascadeType.ALL)
	private StaffAttachmentDTO staffAttachmentDTO;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "staffDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ApprovalDTO> approvalDTOs;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "staffDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ApproverDTO> approverDTOs;
	
	@Column(insertable = false)
	@ColumnDefault("1")
	private Boolean staffLocked;
	@Column(insertable = false)
	@ColumnDefault("1")
	private Boolean staffEnabled;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "staffDTO", cascade = CascadeType.ALL)
	private List<RideDTO> rideDTOs;
	
	
	// For Input
	@Transient
	private Integer inputDeptCode;
	@Transient
	private Integer inputJobCode;
	
	@Override
	public String getUsername() {
		return String.valueOf(staffCode);
	}
	
	@Override
	public String getPassword() {
		return staffPw;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantList = new ArrayList<>();
		grantList.add(new SimpleGrantedAuthority(deptDTO.getDeptName()));
		grantList.add(new SimpleGrantedAuthority(jobDTO.getJobName()));
		
		return grantList;
	}

	@Override
	public boolean isAccountNonLocked() {
		return staffLocked;
	}

	@Override
	public boolean isEnabled() {
		return staffEnabled;
	}
	
}

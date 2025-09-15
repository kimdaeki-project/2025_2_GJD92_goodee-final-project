package com.goodee.finals.drive;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drive_share")
public class DriveShareDTO {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staffCode", nullable = false)
	@Id
	private StaffDTO staffDTO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driveNum", nullable = false)
	@Id
	private DriveDTO driveDTO;
	
	
	
}

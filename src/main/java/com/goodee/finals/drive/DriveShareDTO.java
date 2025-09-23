package com.goodee.finals.drive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "drive_share")
@IdClass(DriveShareDTO.PK.class)
public class DriveShareDTO {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staffCode", nullable = false)
	@Id
	private StaffDTO staffDTO;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driveNum", nullable = false)
	@Id
	private DriveDTO driveDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer staffDTO;
		private Long driveDTO;
	}
	
}

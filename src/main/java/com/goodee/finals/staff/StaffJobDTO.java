package com.goodee.finals.staff;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "staff_job")
@IdClass(StaffJobDTO.PK.class)
public class StaffJobDTO {
	@Id
	@OneToOne
	@JoinColumn(name = "staffCode", insertable = false, updatable = false)
	private StaffDTO staffDTO;
	@Id
	@ManyToOne
	@JoinColumn(name = "jobCode", insertable = false, updatable = false)
	private JobDTO jobDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK implements Serializable {
		private Integer staffDTO;
		private Integer jobDTO;
	}
	
}

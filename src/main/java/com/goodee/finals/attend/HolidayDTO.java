package com.goodee.finals.attend;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity @Table(name = "holiday")
public class HolidayDTO {

	@Id
	private LocalDate date;
	private String kind;
	
}

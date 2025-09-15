package com.goodee.finals.attend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "attend")
public class AttendDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attendNum;
	
	private LocalDate attendDate = LocalDate.now();
	private LocalTime attendIn = LocalTime.now();
	private LocalTime attendOut;
	
	// 출퇴근 사용자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	public void setAttendIn(LocalTime attendIn) {
        this.attendIn = attendIn;
    }

    // ✅ 포맷된 문자열 반환용 메서드 추가
    public String getFormattedAttendIn() {
        if (attendIn == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return attendIn.format(formatter);
    }
	
}

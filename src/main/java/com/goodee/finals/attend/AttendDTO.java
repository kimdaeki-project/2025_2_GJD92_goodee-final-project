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
    // ✅ 포맷된 문자열 반환용 메서드 추가
    public String getFormattedAttendOut() {
    	if (attendIn == null) return "";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    	return attendOut.format(formatter);
    }
    
    public String getWorkTime() {
        if (attendIn == null || attendOut == null) {
            return "--:--:--";
        }

        // 1. LocalTime -> 나노초 변환
        long inNano = attendIn.toNanoOfDay();
        long outNano = attendOut.toNanoOfDay();

        // 2. 차이 계산 (퇴근 - 출근)
        long workNano = outNano - inNano;

        // 3. 시/분/초로 변환
        long hours = workNano / 3_600_000_000_000L;          // 1시간 = 3.6조 ns
        long minutes = (workNano / 60_000_000_000L) % 60;    // 1분 = 60초
        long seconds = (workNano / 1_000_000_000L) % 60;     // 1초 = 10억 ns

        // 4. 원하는 포맷으로 반환
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
	
}

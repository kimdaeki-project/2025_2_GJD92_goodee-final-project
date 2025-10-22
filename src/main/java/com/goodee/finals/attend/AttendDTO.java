package com.goodee.finals.attend;

import java.time.Duration;
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
        	return "-- --";
        }

        Duration duration = Duration.between(attendIn, attendOut);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format("%02dh %02dm", hours, minutes);
    }
    
    public String getTotalWorkTime() {
        if (attendIn == null || attendOut == null) {
            return "--h --m";
        }

        Duration duration = Duration.between(attendIn, attendOut);

        // 휴게시간 1시간 빼는 조건 체크
        boolean isRestApplicable = attendIn.isBefore(LocalTime.of(10, 0)) && attendOut.isAfter(LocalTime.of(18, 0));

        if (isRestApplicable) {
            duration = duration.minusHours(1);
            if (duration.isNegative()) {
                duration = Duration.ZERO; // 음수 방지
            }
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format("%02dh %02dm", hours, minutes);
    }
    
    public String getAttendStatus() {
        if (attendIn == null && attendOut == null) {
            return "결근";
        }

        boolean isLate = attendIn != null && attendIn.isAfter(LocalTime.of(9, 0));
        boolean isEarlyLeave = attendOut != null && attendOut.isBefore(LocalTime.of(18, 0));

        if (isLate && isEarlyLeave) return "지각, 조퇴(미승인)";
        if (isLate) return "지각";
        if (isEarlyLeave) return "조퇴(미승인)";

        return "-";
    }
    
}

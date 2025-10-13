package com.goodee.finals.attend;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.approval.EarlyDTO;
import com.goodee.finals.approval.OvertimeDTO;
import com.goodee.finals.approval.VacationDTO;
import com.goodee.finals.approval.VacationRepository;
import com.goodee.finals.attend.AttendController.WeeklyWorkResult;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendService {

	@Autowired
	AttendRepository attendRepository;
	@Autowired
	StaffRepository staffRepository;
	@Autowired
	VacationRepository vacationRepository;

	public AttendDTO attendIn(AttendDTO attendDTO) {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        // 오늘 이미 출근했는지 확인
        if (attendRepository.existsByStaffDTOStaffCodeAndAttendDate(staffCode, today)) {
            throw new IllegalStateException("오늘은 이미 출근 기록이 있습니다.");
        }

        StaffDTO staff = staffRepository.findById(staffCode)
                .orElseThrow(() -> new IllegalStateException("직원 정보를 찾을 수 없습니다."));

        attendDTO.setStaffDTO(staff);
        attendDTO.setAttendDate(today);
        attendDTO.setAttendIn(LocalTime.now());

        return attendRepository.save(attendDTO);
	}
	
	public AttendDTO attendOut(AttendDTO attendDTO) {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        AttendDTO attend = attendRepository.findByStaffDTOStaffCodeAndAttendDate(staffCode, today)
                .orElseThrow(() -> new IllegalStateException("출근 기록이 없습니다."));

        if (attend.getAttendOut() != null) {
            throw new IllegalStateException("이미 퇴근 기록이 있습니다. 인사 담당자에게 문의하세요.");
        }

        attend.setAttendOut(LocalTime.now());
        return attendRepository.save(attend);
		
	}
	
	public AttendDTO findAttend() {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        return attendRepository.findByStaffDTOStaffCodeAndAttendDate(staffCode, today)
                .orElse(null); // 있으면 AttendDTO, 없으면 null
	}
	
	public long getLateCount(int staffCode, int year, int month) {
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
	    LocalTime standardTime = LocalTime.of(9, 0); // 예: 9시 이후면 지각
	    return attendRepository.countLateByStaffCodeInMonth(staffCode, standardTime, startDate, endDate);
	}

	public long getEarlyLeaveCount(int staffCode, int year, int month) {
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
	    LocalTime standardTime = LocalTime.of(18, 0); // 예: 18시 이전 퇴근은 조퇴
	    return attendRepository.countEarlyLeaveByStaffCodeInMonth(staffCode, standardTime, startDate, endDate);
	}
	
	public long getAbsentCount(Integer staffCode, int year, int month, LocalDate today) {
		String monthStr = (month < 10) ? "0" + month : "" + month;
	    monthStr = year + monthStr;
	    
	    List<HolidayDTO> holidayList = attendRepository.findByMonth(monthStr);
		List<Integer> holiday = new ArrayList<>();
		for (HolidayDTO holidayDTO : holidayList) {
			holiday.add(holidayDTO.getDate().getDayOfMonth());
		}
		
		return attendRepository.countAbsentDays(year, month, staffCode, today, holiday);
	}
	
	public WeeklyWorkResult getWeeklyWorkTime(LocalDate monday, LocalDate sunday, Integer staffCode) {
		// 선택된 한 주의 월~일요일까지의 출퇴근내역
		// 월요일의날짜시작~일요일의날짜끝으로 그 날짜 사이에 공휴일,연차,조퇴 있다면, 그냥 total에 8시간 더해주기
		// 연장근로 total 8 + 추가근로시간
		
        List<AttendDTO> records = attendRepository
                .findByStaffDTO_StaffCodeAndAttendDateBetween(staffCode, monday, sunday);
//        List<HolidayDTO> weeklyHoliday = holidayRepository.findByStaffDTO_StaffCodeAndAttendDateBetween;
//        List<VacationDTO> weeklyVacation = vacationRepository.findByStaffDTO_StaffCodeAndAttendDateBetween;
//        List<EarlyDTO> weeklyEarly = earlyRepository.findByStaffDTO_StaffCodeAndAttendDateBetween;
//        List<OvertimeDTO> weeklyOvertime = overtimeRepository.findByStaffDTO_StaffCodeAndAttendDateBetween;
        
        
        Duration total = Duration.ZERO;      // 총 근로시간
        Duration overtime = Duration.ZERO;   // 일일 연장근로시간
        Duration weeklyOvertime = Duration.ZERO; // 주 40시간 초과분

        for (AttendDTO record : records) {
            if (record.getAttendIn() != null && record.getAttendOut() != null) {
                LocalTime in = record.getAttendIn();
                LocalTime out = record.getAttendOut();

                Duration duration = Duration.between(in, out);

                // 휴게시간 1시간 차감 (12~13시 걸치는 경우)
                boolean isRestApplicable = in.isBefore(LocalTime.NOON) && out.isAfter(LocalTime.of(13, 0));
                if (isRestApplicable) {
                    duration = duration.minusHours(1);
                }

                if (!duration.isNegative()) {
                    total = total.plus(duration);

                    // 일일 연장근로 (9시간 초과)
                    if (duration.toMinutes() > 540) {
                        overtime = overtime.plusMinutes(duration.toMinutes() - 540);
                    }
                }
            }
        }

        // 주 40시간 초과분 계산
        if (total.toMinutes() > 2400) {
            weeklyOvertime = weeklyOvertime.plusMinutes(total.toMinutes() - 2400);
        }

        return new WeeklyWorkResult(
                formatDuration(total),
                formatDuration(overtime),
                formatDuration(weeklyOvertime)
        );
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02dh %02dm", hours, minutes);
    }
	
	public Page<AttendDTO> getMonthlyAttendances(Integer staffCode, int year, int month, LocalDate today, Pageable pageable) {
		
		String monthStr = (month < 10) ? "0" + month : "" + month;
	    monthStr = year + monthStr;
	    
	    // 휴무일 리스트 가져오기
		List<HolidayDTO> holidayList = attendRepository.findByMonth(monthStr);
		List<Integer> holiday = new ArrayList<>();
		for (HolidayDTO holidayDTO : holidayList) {
			holiday.add(holidayDTO.getDate().getDayOfMonth());
		}
		
		// 연차 리스트 가져오기
		List<VacationDTO> vacationList = attendRepository.findAllVacationByStaffCodeAndByMonth(staffCode, monthStr);
		for (VacationDTO vacationDTO : vacationList) {
			
			Integer vacStartDay = vacationDTO.getVacStart().getDayOfMonth();
			Integer vacEndDay = vacationDTO.getVacEnd().getDayOfMonth();
			
			if (vacationDTO.getVacStart().getDayOfMonth() != vacationDTO.getVacEnd().getDayOfMonth()) {
				holiday.add(vacStartDay);
				holiday.add(vacEndDay);
			} else {
				holiday.add(vacStartDay);
			}
		}
		
		// 출퇴근내역에서 오늘자까지의 기록만 가져오기
        return attendRepository.findMonthlyAttendancesUntilToday(year, month, staffCode, today, pageable, holiday);
    }
	
	// 연장근로 날짜와 시간 가져오기
	public List<OvertimeDTO> findAllOvertimeByStaffCodeAndByMonth(Integer staffCode, int year, int month){
		String monthStr = (month < 10) ? "0" + month : "" + month;
	    monthStr = year + monthStr;
		
		return attendRepository.findAllOvertimeByStaffCodeAndByMonth(staffCode, monthStr);
	}
	
	// 조기퇴근 날짜와 시간 가져오기
	public List<EarlyDTO> findAllEarlyByStaffCodeAndByMonth(Integer staffCode, int year, int month){
		String monthStr = (month < 10) ? "0" + month : "" + month;
		monthStr = year + monthStr;
		
		return attendRepository.findAllEarlyByStaffCodeAndByMonth(staffCode, monthStr);
	}
	
}

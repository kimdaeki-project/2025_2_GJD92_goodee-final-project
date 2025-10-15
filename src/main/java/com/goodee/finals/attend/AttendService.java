package com.goodee.finals.attend;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
	
	private Duration tempDur = null;

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
	
	// 주 근로시간 계산
	public WeeklyWorkResult getWeeklyWorkTime(LocalDate monday, LocalDate sunday, Integer staffCode) {
		
		// 주 단위 출퇴근내역
		List<AttendDTO> records = attendRepository.findByStaffDTO_StaffCodeAndAttendDateBetween(staffCode, monday, sunday);

		// 주 단위 데이터
		List<HolidayDTO> weeklyOnlyHolidayList = attendRepository.findOnlyHolidayByWeek(staffCode, monday, sunday); // 공휴일
		List<VacationDTO> weeklyVacationList = attendRepository.findVacationByStaffCodeAndWeek(staffCode, monday, sunday); // 휴가
		List<EarlyDTO> weeklyEarlyList = attendRepository.findEarlyByStaffCodeAndWeek(staffCode, monday, sunday); //조기퇴근
		List<OvertimeDTO> weeklyOvertimeList = attendRepository.findOvertimeByStaffCodeAndWeek(staffCode, monday, sunday); // 연장근로
		
		for (HolidayDTO HolidayDTO : weeklyOnlyHolidayList) {
			System.out.println(HolidayDTO.getDate()+" : "+HolidayDTO.getKind());
		}
		
        Duration total = Duration.ZERO;      // 총 근로시간
        Duration overtime = Duration.ZERO;   // 일일 연장근로시간
        Duration weeklyOvertime = Duration.ZERO; // 주 40시간 초과분
        
        for (AttendDTO record : records) {
        	
        	LocalDate attendDate = record.getAttendDate();

        	boolean isHoliDay = weeklyOnlyHolidayList.stream().anyMatch(hol ->
	            attendDate.equals(hol.getDate()) 
	        );
        	
        	boolean isVacationDay = weeklyVacationList.stream().anyMatch(vac ->
        	(attendDate.equals(vac.getVacStart()) || attendDate.equals(vac.getVacEnd())) ||
        	(attendDate.isAfter(vac.getVacStart()) && attendDate.isBefore(vac.getVacEnd()))
        			);
        	
        	// 1. 공휴일
        	if(isHoliDay) {
        		total = total.plusHours(8);
                System.out.println(attendDate + "는 공휴일입니다.");
                
            // 2. 휴가일
        	} else if(isVacationDay){
        		total = total.plusHours(8);
                System.out.println(attendDate + "는 휴가일입니다.");
                
            // 3. 근무일 -> [정상근무 / 조기퇴근 / 결근 / 연장근무] 구분 *놀이공원이라서 주말도 정규근무일로 가정한다면..
            } else {
                System.out.println(attendDate + "는 근무일입니다.");
                
                // 3-1. 출근&퇴근시간 다 없을 때
                // 1) 결근
                if (record.getAttendIn() == null && record.getAttendOut() == null) {
                	System.out.println(attendDate + "에 결근하였습니다.");
                	total = total.plusHours(0);
                }
                
                // 3-2. 출근&퇴근시간 둘 중 하나만 있을 때 (계산하지 않기) -> 추후 인사팀에 문의하는 것으로..
                if (record.getAttendIn() != null || record.getAttendOut() != null) {
                	System.out.println(attendDate + "에 출근 또는 퇴근 기록이 빠졌습니다.");
                	total = total.plusHours(0);
                }
                
                // 3-3. 출근&퇴근시간 다 있을 때
                if (record.getAttendIn() != null && record.getAttendOut() != null) {
                	
                	// 1) 조기퇴근
                	boolean isEarlyDay = weeklyEarlyList.stream().anyMatch(early ->
                	attendDate.equals(early.getEarlyDtm().toLocalDate())
                			);
                	
                	if (isEarlyDay) {
						 weeklyEarlyList.stream()
						    .filter(early -> attendDate.equals(early.getEarlyDtm().toLocalDate()))
						    .findFirst()
						    .ifPresent(early -> {
						        // attendOut 시간을 조기퇴근 승인시간으로 덮어쓰기
						        record.setAttendOut(early.getEarlyDtm().toLocalTime());
						    });
						
						Duration duration = Duration.between(record.getAttendIn(), record.getAttendOut());
						// 휴게시간 1시간 뺀 시간으로 추가해줘야함..
						total = total.plus(duration);
						
						System.out.println(attendDate + "에 조기퇴근하였습니다. 퇴근시간: " + record.getAttendOut());
						
                	} else {
                		// 2) 연장근무
                		boolean isOvertimeDay = weeklyOvertimeList.stream().anyMatch(ot ->
                    	attendDate.equals(ot.getOverStart().toLocalDate())
                    			);
                    	
                    	if (isOvertimeDay) {
                    		long timeTmp = 0;
                    		for (OvertimeDTO overtimeDTO : weeklyOvertimeList) {
                    			timeTmp += overtimeDTO.getOverEnd().atZone(ZoneId.systemDefault()).toEpochSecond() - overtimeDTO.getOverStart().atZone(ZoneId.systemDefault()).toEpochSecond();
							}
                    		total = total.plusHours(8);
                    		overtime = overtime.plusHours(timeTmp/60/60);
                    		
    						System.out.println(attendDate + "에 연장근무하였습니다. 연장근무: " + attendDate + overtime.toHours());
                		
                    	} else {
                    		// 3) 정상근무
                    		// 근무시간이 6시간은 넘는지..?
                    		LocalTime in = record.getAttendIn();
                            LocalTime out = record.getAttendOut();

                            Duration duration = Duration.between(in, out);
                            
                    		if(duration.toHours() > 5) {
                    			total = total.plusHours(8);
                    		}else {
                    			total = total.plusHours(0);
                    		}
                    	}
                    	
//                		// 일일 연장근로 (9시간 초과)
//                		if (duration.toMinutes() > 540) {
//                			overtime = overtime.plusMinutes(duration.toMinutes() - 540);
//                		}
                	}
                }
                
            } // 근무일일때 else문
        } // for문

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

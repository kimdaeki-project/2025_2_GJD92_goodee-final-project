package com.goodee.finals.attend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.approval.EarlyDTO;
import com.goodee.finals.approval.OvertimeDTO;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/attend/**")
@Slf4j
public class AttendController {

	@Autowired
	private AttendService attendService;
	
	@Autowired
	private StaffService staffService;
	
	// 대시보드
	@PostMapping("in")
	public String attendIn(AttendDTO attendDTO, Model model) {
		
		String resultMsg = "출근처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		String resultUrl = "/";

		try {
			AttendDTO result = attendService.attendIn(attendDTO);
			model.addAttribute("attendDTO", result);
			
			resultMsg = "출근처리가 완료되었습니다.";
			resultIcon = "success";
			log.info("출근체크");
			
		} catch (IllegalStateException e) {
			// TODO: handle exception
			resultMsg = "오늘은 이미 출근 기록이 있습니다.";
			resultIcon = "warning";
			log.warn("출근체크 실패: {}", e.getMessage());
		} catch (Exception e) {
            resultMsg = "출근처리 중 오류가 발생했습니다.";
            resultIcon = "error";
            log.error("출근체크 에러", e);
        }
			
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		model.addAttribute("resultUrl", resultUrl);
			
		return "common/result";
		
	}
	
	@PostMapping("out")
	public String attendOut(AttendDTO attendDTO, Model model) {
		AttendDTO result = attendService.attendOut(attendDTO);
		
		String resultMsg = "퇴근처리 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "퇴근처리가 완료되었습니다.";
			resultIcon = "success";
			String resultUrl = "/";
			model.addAttribute("attendDTO", result);
			model.addAttribute("resultUrl", resultUrl);
			log.info("퇴근체크");
			
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		} else {
			return null;
		}
	}
	
	// 근태 페이지
	@GetMapping("")
	public String list(@RequestParam(required = false) Integer year,
			            @RequestParam(required = false) Integer month,
			            @RequestParam(value = "baseDate", required = false) String baseDateStr,
			            @PageableDefault(size = 10, sort = "attendDate", direction = Direction.DESC ) Pageable pageable,
			            Model model) {
		
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		StaffDTO staffDTO = staffService.getStaff(staffCode);
		model.addAttribute("staffDTO", staffDTO);
	    
		LocalDate baseDate = (baseDateStr == null) ? LocalDate.now() : LocalDate.parse(baseDateStr);
		LocalDate today = LocalDate.now();
		
		// 주 근로시간
		LocalDate monday = baseDate.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = baseDate.with(java.time.temporal.TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // 이전/다음 주 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");
        LocalDate prevWeek = monday.minusWeeks(1);
        LocalDate nextWeek = monday.plusWeeks(1);
        model.addAttribute("mondayStr", monday.format(formatter));
        model.addAttribute("sundayStr", sunday.format(formatter));
        model.addAttribute("monday", monday);
        
        // DB에서 주간 근로시간 합계 조회
        WeeklyWorkResult result = attendService.getWeeklyWorkTime(monday, sunday, staffCode);
        model.addAttribute("totalWorkTime", result.getTotalWorkTime());
        model.addAttribute("overtimeWorkTime", result.getOvertimeWorkTime());
        
        int weekHours = Integer.parseInt(result.getTotalWorkTime().split("h")[0].trim());
        int overHours = Integer.parseInt(result.getOvertimeWorkTime().split("h")[0].trim());
        int weekPercent = (int) Math.round((weekHours / 40.0) * 100);
        int overPercent = (int) Math.round((overHours / 12.0) * 100);
        model.addAttribute("weekPercent", weekPercent);
        model.addAttribute("overPercent", overPercent);
        
     // ✅ 1. totalWorkTime 문자열을 분으로 변환
        String totalWorkTimeStr = result.getTotalWorkTime();
        int totalWorkedMinutes = parseWorkTimeToMinutes(totalWorkTimeStr);

        // ✅ 2. 최대 근로시간 (40시간 = 2400분)
        int maxWeeklyMinutes = 40 * 60;

        // ✅ 3. 잔여 근로시간 계산
        int remainingMinutes = Math.max(0, maxWeeklyMinutes - totalWorkedMinutes);

        // ✅ 4. 다시 hh mm 포맷으로 변환
        String remainingWorkTime = formatMinutesToHhMm(remainingMinutes);

        // ✅ 5. JSP에 전달
        model.addAttribute("remainingWorkTime", remainingWorkTime);
        
        model.addAttribute("prevWeek", prevWeek);
        model.addAttribute("nextWeek", nextWeek);
        
	    // 출퇴근 내역
	    int targetYear = (year == null) ? today.getYear() : year;
	    int targetMonth = (month == null) ? today.getMonthValue() : month;
	    model.addAttribute("year", targetYear);
	    model.addAttribute("month", targetMonth);
	    
	    Page<AttendDTO> attendances = attendService.getMonthlyAttendances(staffCode, targetYear, targetMonth, today, pageable);
	    model.addAttribute("attendances", attendances);
	    
	    // 지각 갯수
	    long lateCount = attendService.getLateCount(staffCode, targetYear, targetMonth);
	    model.addAttribute("lateCount", lateCount);
	    
	    // 조퇴 갯수
	    long earlyLeaveCount = attendService.getEarlyLeaveCount(staffCode, targetYear, targetMonth);
	    model.addAttribute("earlyLeaveCount", earlyLeaveCount);
	    
	    // 결근 갯수
	    long absentCount = attendService.getAbsentCount(staffCode, targetYear, targetMonth, today);
	    model.addAttribute("absentCount", absentCount);
		
	    // 연장근로 리스트
	    List<OvertimeDTO> overtimeList = attendService.findAllOvertimeByStaffCodeAndByMonth(staffCode, targetYear, targetMonth);
	    model.addAttribute("overtimeList", overtimeList);
	    
	    // 조기퇴근 리스트
	    List<EarlyDTO> earlyList = attendService.findAllEarlyByStaffCodeAndByMonth(staffCode, targetYear, targetMonth);
	    model.addAttribute("earlyList", earlyList);
	    
		return "attend/list";
	}
	
	@GetMapping("weeklyData")
	@ResponseBody
	public Map<String, Object> getWeeklyDataAjax(@RequestParam String baseDate) {
	    Map<String, Object> result = new HashMap<>();

	    Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
	    LocalDate base = LocalDate.parse(baseDate);
	    LocalDate monday = base.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	    LocalDate sunday = base.with(java.time.temporal.TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

	    WeeklyWorkResult work = attendService.getWeeklyWorkTime(monday, sunday, staffCode);

	    int totalWorkedMinutes = parseWorkTimeToMinutes(work.getTotalWorkTime());
	    int remainingMinutes = Math.max(0, 40 * 60 - totalWorkedMinutes);
	    String remainingWorkTime = formatMinutesToHhMm(remainingMinutes);

	    result.put("mondayStr", monday.format(DateTimeFormatter.ofPattern("M/d")));
	    result.put("sundayStr", sunday.format(DateTimeFormatter.ofPattern("M/d")));
	    result.put("prevWeek", monday.minusWeeks(1).toString());
	    result.put("nextWeek", monday.plusWeeks(1).toString());
	    result.put("totalWorkTime", work.getTotalWorkTime());
	    result.put("overtimeWorkTime", work.getOvertimeWorkTime());
	    result.put("remainingWorkTime", remainingWorkTime);

	    return result;
	}
	
	@GetMapping("monthlyData")
	@ResponseBody
	public Map<String, Object> getMonthlyAttendancesAjax(
	        @RequestParam Integer year,
	        @RequestParam Integer month,
	        @PageableDefault(size = 10, sort = "attendDate", direction = Direction.DESC) Pageable pageable) {

	    Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
	    LocalDate today = LocalDate.now();

	    Page<AttendDTO> attendances = attendService.getMonthlyAttendances(staffCode, year, month, today, pageable);
	    List<OvertimeDTO> overtimeList = attendService.findAllOvertimeByStaffCodeAndByMonth(staffCode, year, month);

	    Map<String, Object> result = new HashMap<>();
	    result.put("attendances", attendances.getContent());
	    result.put("totalPages", attendances.getTotalPages());
	    result.put("currentPage", attendances.getNumber());
	    result.put("overtimeList", overtimeList);
	    result.put("year", year);
	    result.put("month", month);
	    return result;
	}
	
	public static class WeeklyWorkResult {
		private String totalWorkTime;      // 총 근로시간
	    private String overtimeWorkTime;   // 연장근로 (1일 9시간 초과분)

	    public WeeklyWorkResult(String totalWorkTime, String overtimeWorkTime) {
	        this.totalWorkTime = totalWorkTime;
	        this.overtimeWorkTime = overtimeWorkTime;
	    }

	    public String getTotalWorkTime() { return totalWorkTime; }
	    public String getOvertimeWorkTime() { return overtimeWorkTime; }
	}
	
	private int parseWorkTimeToMinutes(String workTime) {
	    if (workTime == null || !workTime.contains("h")) return 0;

	    try {
	        String[] parts = workTime.split("h|m");
	        int hours = Integer.parseInt(parts[0].trim());
	        int minutes = Integer.parseInt(parts[1].trim());
	        return hours * 60 + minutes;
	    } catch (Exception e) {
	        return 0;
	    }
	}

	private String formatMinutesToHhMm(int minutes) {
	    int h = minutes / 60;
	    int m = minutes % 60;
	    return String.format("%02dh %02dm", h, m);
	}
}

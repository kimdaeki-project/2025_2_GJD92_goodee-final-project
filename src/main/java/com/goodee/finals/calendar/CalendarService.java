package com.goodee.finals.calendar;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

/*
캘린더에 저장되는 일정과 실제 풀캘린더에 필요한 일정이 다름
예) 10-10 ~ 10-11을 종일 일정으로 설정하면 하루로 취급함 - Exclusive(베타적)으로취급
DB에 저장시 사용자가 입력한 값 그대로 저장
DB에서 조회시 calEnd(종료일) + 1 하여 반환

풀캘린더의 드래그드롭, 일정기간 늘리기 기능 사용시 이미 + 1증가된 값
해당 기능 사용시에는 DB저장전에 종료일 - 1
 */

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CalendarService {
	
	public static final String INSPECTION = "점검";
	public static final String COMPANY = "사내";
	public static final String DEPT = "부서";
	
	@Autowired
	private CalendarRepository calendarRepository;

	public List<CalTypeDTO> getCalTypesByDept(StaffDTO staffDTO) {
		return calendarRepository.getCalTypesByDept(staffDTO.getDeptDTO().getDeptCode());
	}

	public List<CalendarDTO> getCalendarList(StaffDTO staffDTO, List<Integer> calTypes) { 
		return calendarRepository.getCalendarList(staffDTO.getDeptDTO().getDeptCode(), calTypes);
	}
	
	public CalendarDTO getCalendar(Long calNum) {
		CalendarDTO calendarDTO = calendarRepository.findById(calNum).orElseThrow();
		if(calendarDTO == null) {
			return null;
		}
		return calendarDTO;
	}
	
	public CalendarDTO addCalendar(StaffDTO staffDTO, CalendarDTO calendarDTO) {
		if(calendarDTO == null) return null;
		
		switch (calendarDTO.getCalType()) {
		case 2001: calendarDTO.setCalTypeName(INSPECTION); break;
		case 2002: calendarDTO.setCalTypeName(COMPANY); break;
		case 2003: calendarDTO.setCalTypeName(DEPT);
				   DeptDTO deptDTO = new DeptDTO();
				   deptDTO.setDeptCode(staffDTO.getDeptDTO().getDeptCode());
				   calendarDTO.setDeptDTO(deptDTO);
				   break;
		}
		calendarDTO.setCalReg(LocalDateTime.now());
		calendarDTO.setCalEnabled(true);
		calendarDTO.setStaffDTO(staffDTO);
		calendarDTO = calendarRepository.save(calendarDTO);
		
		return calendarDTO;
	}
	
	
	public CalendarDTO updateCalendar(CalendarDTO calendarDTO, StaffDTO staffDTO) {
		CalendarDTO oriCalendar = calendarRepository.findById(calendarDTO.getCalNum()).orElseThrow();
		if(!staffDTO.getStaffCode().equals(oriCalendar.getStaffDTO().getStaffCode())) {
			return null;
		}
		
		switch (calendarDTO.getCalType()) {
		case 2001: oriCalendar.setCalTypeName(INSPECTION); break;
		case 2002: oriCalendar.setCalTypeName(COMPANY); break;
		case 2003: oriCalendar.setCalTypeName(DEPT);
				   if(oriCalendar.getDeptDTO() != null) break;
				   DeptDTO deptDTO = new DeptDTO();
				   deptDTO.setDeptCode(staffDTO.getDeptDTO().getDeptCode());
				   oriCalendar.setDeptDTO(deptDTO);
				   break;
		}
		oriCalendar.setCalTitle(calendarDTO.getCalTitle());
		oriCalendar.setCalPlace(calendarDTO.getCalPlace());
		oriCalendar.setCalContent(calendarDTO.getCalContent());
		oriCalendar.setCalIsAllDay(calendarDTO.getCalIsAllDay());
		oriCalendar.setCalStart(calendarDTO.getCalStart());
		oriCalendar.setCalEnd(calendarDTO.getCalEnd());
		
		oriCalendar = calendarRepository.save(oriCalendar);
		
		return oriCalendar;
	}
	
	public boolean disableEvent(CalendarDTO calendarDTO, StaffDTO staffDTO) {
		CalendarDTO oriCalendar = calendarRepository.findById(calendarDTO.getCalNum()).orElseThrow();
		if(!staffDTO.getStaffCode().equals(oriCalendar.getStaffDTO().getStaffCode())) {
			System.out.println("calendarService : 등록자가 아니라 삭제 할 수 없음");
			return false;
		}
		oriCalendar.setCalEnabled(false);
		oriCalendar = calendarRepository.save(oriCalendar);
		if(!oriCalendar.getCalEnabled()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateCalDate(CalendarDTO calendarDTO) {
		CalendarDTO oriCal = calendarRepository.findById(calendarDTO.getCalNum()).orElseThrow();
		oriCal.setCalStart(calendarDTO.getCalStart());
		oriCal.setCalEnd(calendarDTO.getCalEnd());
		oriCal = calendarRepository.save(oriCal);
		if(oriCal.getCalStart().isEqual((calendarDTO.getCalStart()))) {
			return true;
		}
		return false; 
	}
	
}

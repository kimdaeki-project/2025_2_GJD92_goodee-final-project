package com.goodee.finals.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;

@Service
public class CalendarService {
	public static final String INSPECTION = "점검";
	public static final String COMPANY = "사내";
	public static final String DEPT = "부서";
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	public List<CalendarDTO> getEventList(StaffDTO staffDTO) { 
		return calendarRepository.getEventList(staffDTO.getDeptDTO().getDeptCode());
	}
	
	public CalendarDTO addEvent(StaffDTO staffDTO, CalendarDTO calendarDTO) {
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
		calendarDTO.setCalEnabled(true);
		calendarDTO.setStaffDTO(staffDTO);
		
//		calendarDTO = calendarRepository.save(calendarDTO);
		
		return calendarDTO;
	}
	
}

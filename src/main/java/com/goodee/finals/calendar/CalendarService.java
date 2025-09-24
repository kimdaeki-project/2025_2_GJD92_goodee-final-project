package com.goodee.finals.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;

@Service
public class CalendarService {
 
	@Autowired
	private CalendarRepository calendarRepository;
	
	public List<CalendarDTO> getEventList(StaffDTO staffDTO) { 
		return calendarRepository.getEventList(staffDTO.getDeptDTO().getDeptCode());
	}
	
}

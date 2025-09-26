package com.goodee.finals.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/calendar/**")
@Slf4j
public class CalendarController {
	
	@Autowired
	private CalendarService calendarService;
	
	@GetMapping
	public String calendar() {
		return "calendar/calendar";
	}
	
	@GetMapping("eventList")
	@ResponseBody
	public List<CalendarDTO> getEventList() {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<CalendarDTO> list = calendarService.getEventList(staffDTO);
		
		if(list == null || list.isEmpty()) {
			list = null;
		}
		return list;
	}
	
	@PostMapping("add")
	@ResponseBody
	public CalendarDTO addEvent(@RequestBody CalendarDTO calendarDTO, Authentication authentication) {
		StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
		
		System.out.println("캘린더 종료일 : " + calendarDTO.getCalEnd());
		
		calendarDTO = calendarService.addEvent(staffDTO, calendarDTO);
		
		return calendarDTO;
	}
	
	
}

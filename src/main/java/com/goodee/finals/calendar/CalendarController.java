package com.goodee.finals.calendar;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String calendar(Authentication authentication, Model model) {
		StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
		List<CalTypeDTO> calTypeList = calendarService.getCalTypesByDept(staffDTO);
		
		model.addAttribute("staffDTO", staffDTO);
		model.addAttribute("calTypeList", calTypeList);
		
		return "calendar/calendar";
	}
	
	@GetMapping("eventList")
	@ResponseBody
	public List<CalendarDTO> getCalendarList(@RequestParam(required = false) List<Integer> calTypes) {
		if(calTypes == null || calTypes.size() < 1 ) { // 체크된 일정타입이 없으면 바로 return
			return Collections.emptyList();
		}
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<CalendarDTO> list = calendarService.getCalendarList(staffDTO, calTypes);
		
		if(list == null || list.isEmpty()) {
			list = null;
		}
		return list;
	}
	
	@PostMapping("add")
	@ResponseBody
	public CalendarDTO addCalendar(@RequestBody CalendarDTO calendarDTO, Authentication authentication) {
		StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
		calendarDTO = calendarService.addCalendar(staffDTO, calendarDTO);
		
		return calendarDTO;
	}
	
	@GetMapping("{calNum}")
	@ResponseBody
	public CalendarDTO getCalendar(@PathVariable Long calNum) {
		CalendarDTO result = calendarService.getCalendar(calNum);
		
		return result;
	}
	
	@PostMapping("update")
	@ResponseBody
	public CalendarDTO updateCalendar(@RequestBody CalendarDTO calendarDTO, Authentication authentication) {
		StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
		
		return calendarService.updateCalendar(calendarDTO, staffDTO);
	}
	
	@PostMapping("delete")
	@ResponseBody
	public boolean disableCalendar(CalendarDTO calendarDTO, Authentication authentication) {
		StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
		
		return calendarService.disableEvent(calendarDTO, staffDTO);
	}
	
}

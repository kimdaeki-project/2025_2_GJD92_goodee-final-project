package com.goodee.finals.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/calendar/**")
public class CalendarController {

	@GetMapping
	public String calendar() {
		return "calendar/calendar";
	}
	
}

package com.goodee.finals.weather;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
	
	private WeatherService weatherService;
	
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		double lat = 37.5665;  // 서울 위도
		double lon = 126.9780;  // 서울 경도
		
		WeatherDTO weather = weatherService.getForecast(lat, lon);
		
		model.addAttribute("weather", weather.getCurrentWeather());
		model.addAttribute("location", "서울");
		
		return "/";
		
	}

}

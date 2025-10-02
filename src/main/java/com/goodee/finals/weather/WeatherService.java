package com.goodee.finals.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
	
	private RestTemplate restTemplate;
	
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public WeatherDTO getForecast(double lat, double lon) {
		String url = String.format(
			"https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true&timezone=auto",
			lat, lon
		);
		
		return restTemplate.getForObject(url, WeatherDTO.class);
	}
	
	

}

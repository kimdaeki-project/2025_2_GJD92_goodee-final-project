package com.goodee.finals.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherDTO {

	@JsonProperty("latitude")
	private double latitude;

	@JsonProperty("longitude")
	private double longitude;
	
	@JsonProperty("timezone")
	private String timezone;
	
	@JsonProperty("current_weather")
	private CurrentWeather currentWeather;
	
	
	@Getter
	@Setter
	@ToString
	public static class CurrentWeather {
		
		@JsonProperty("temperature")
		private Double temperture;  // °C
		
		@JsonProperty("windspeed")
		private Double windspeed;  // m/s
		
		@JsonProperty("time")
		private String time;
		
		
	}
	
	
}

package com.goodee.finals.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherDTO {

    private double latitude;  // 위도
    private double longitude;  // 경도

    // API 응답 생성에 걸린 시간
    @JsonProperty("generationtime_ms")
    private double generationTimeMs;

    // UTC 기준 오프셋(초 단위, 한국은 32400 = +9시간)
    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    // Asia/Seoul
    private String timezone;

    // 타임존 약어
    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    // 고도
    private double elevation;

    // 현재 날씨 데이터
    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;
    
    @JsonProperty("hourly")
    private Hourly hourly;


    // 내부 클래스 : 현재 날씨
    @Getter
    @Setter
    @ToString
    public static class CurrentWeather {
        private double temperature;  // 현재 기온
        private double windspeed;  // 풍속
        private double winddirection;  // 풍향
        private int weathercode;  // 날씨 코드
        private String time;  // 측정 시각
        
        @JsonProperty("is_day")
        private int isDay;  // 낮/밤 구분(1=낮, 0=밤)
    }
    
    
    // 내부 클래스 : 시간별 데이터(hourly)
    @Getter
    @Setter
    @ToString
    public static class Hourly {
    	private String[] time;  // 각 시간대별 타임스탬프
    	
    	@JsonProperty("relative_humidity_2m")
    	private double[] relativeHumidity2m;  // 각 시간대별 습도 데이터(%)
    }
    
    
    
}

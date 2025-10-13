package com.goodee.finals.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherDTO {

    private double latitude;
    private double longitude;

    @JsonProperty("generationtime_ms")
    private double generationTimeMs;

    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    private double elevation;

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;


    @Getter
    @Setter
    @ToString
    public static class CurrentWeather {
        private double temperature;
        private double windspeed;
        private double winddirection;
        private int weathercode;
        private String time;
        
        @JsonProperty("is_day")
        private int isDay;
    }
}

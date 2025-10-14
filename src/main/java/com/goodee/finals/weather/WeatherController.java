package com.goodee.finals.weather;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeatherController {

	// 실제 날씨 데이터를 호출
    private final WeatherService weatherService;
    
    // 위도/경도 -> 도시명 반환
    private final WebClient webClient;

    // WebClient.Builder를 통해 두 개의 WebClient를 구성:
    // weatherService: Open-Meteo API 호출
    // webClient: Nomination(OpenStreetMap) API로 도시명 조회
    public WeatherController(WeatherService weatherService, WebClient.Builder builder) {
        this.weatherService = weatherService;
        this.webClient = builder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

    // AJAX 요청용 API (JSON 반환)
    // "/weather/api"로 GET 요청이 오면 JSON 데이터를 반환
    @GetMapping("/weather/api")
    @ResponseBody
    public Map<String, Object> getWeatherJson() {
    	// 기본 좌표값(서울)
        double lat = 37.5665;
        double lon = 126.9780;

        // 응답 데이터를 담을 Map (JSON으로 변환되어 반환)
        Map<String, Object> data = new HashMap<>();
        try {
        	// Open-Meteo API 호출
        	// WeatherService에서 비동기로 호출한 Mono<WeatherDTO>를 block()으로 동기 처리
            var weather = weatherService.getWeather(lat, lon, "Asia/Seoul").block();

            // API 응답이 없거나 current_weather 데이터가 비어 있으면 에러
            if (weather == null || weather.getCurrentWeather() == null) {
                data.put("error", "No weather data received");
                return data;
            }

            // 도시명 조회
            // 위도/경도를 기반으로 Nomination(OpenStreetMap) API를 사용해 도시 이름을 조회
            String city = webClient.get()
                    .uri("/reverse?format=json&lat=" + lat + "&lon=" + lon + "&accept-language=ko")
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(node -> {
                        JsonNode address = node.path("address");
                        if (address.has("city")) return address.get("city").asText();
                        else if (address.has("state")) return address.get("state").asText();
                        else return "대한민국";
                    })
                    .block();

            // 날씨 정보
            var current = weather.getCurrentWeather();  // 현재 날씨 정보(온도, 풍속...)
            
            // 시간별 습도 데이터 추출
            double[] humidities = weather.getHourly().getRelativeHumidity2m();
            double humidity = humidities != null && humidities.length > 0 ? humidities[0] : -1;

            // 클라이언트에 보낼 JSON 데이터 구성
            data.put("city", city);  // 도시명
            data.put("temperature", current.getTemperature());  // 기온
            data.put("windspeed", current.getWindspeed());  // 풍속
            data.put("weathercode", current.getWeathercode());  // 습도
           
            data.put("humidity", humidity);

        // 예외 처리
        // API 호출 또는 파싱 과정에서 오류가 발생하면 에러 메시지를 반환
        } catch (Exception e) {
            data.put("error", e.getMessage());
        }

        // Map 데이터는 JSON 형태로 자동 변환되어 반환
        return data;
    }
    
}

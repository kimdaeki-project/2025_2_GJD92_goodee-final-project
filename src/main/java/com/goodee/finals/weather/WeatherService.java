package com.goodee.finals.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

	// WebClient : 비동기 HTTP 통신을 위한 스프링 제공 클래스
	// Open-Meteo API 기본 URL을 설정하여 날씨 데이터를 요청할 때 사용됨
    private final WebClient webClient;

    // WebClient.Builder를 주입받아 baseUrl 설정
    // (Spring이 자동으로 Bean으로 등록해줌)
    public WeatherService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.open-meteo.com").build();
    }

    public Mono<WeatherDTO> getWeather(double lat, double lon, String timezone) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")  // API 기본 경로
                        .queryParam("latitude", lat)  // 위도
                        .queryParam("longitude", lon)  // 경도
                        .queryParam("current_weather", "true")  // 현재 날씨 (온도, 풍속, 날씨 코드 등 현재 시간대 데이터 제공)
                        .queryParam("hourly", "relative_humidity_2m")  // 시간별 데이터(hourly) 요청 항목
                        .queryParam("timezone", timezone)  // Asia/Seoul
                        .build())  // URI 생성
                .retrieve()  // 요청 실행 및 응답 수신
                .bodyToMono(WeatherDTO.class);  // JSON -> WeatherDTO 클래스 자동 변환
        
        
    }
}

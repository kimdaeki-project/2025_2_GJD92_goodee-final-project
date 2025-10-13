package com.goodee.finals.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.open-meteo.com").build();
    }

    public Mono<WeatherDTO> getWeather(double lat, double lon, String timezone) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("current_weather", "true")
                        .queryParam("hourly", "relative_humidity_2m")
                        .queryParam("timezone", timezone)
                        .build())
                .retrieve()
                .bodyToMono(WeatherDTO.class);
        
        
    }
}

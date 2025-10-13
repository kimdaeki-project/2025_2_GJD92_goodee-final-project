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

    private final WeatherService weatherService;
    private final WebClient webClient;

    public WeatherController(WeatherService weatherService, WebClient.Builder builder) {
        this.weatherService = weatherService;
        this.webClient = builder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

    // AJAX 요청용 API (JSON 반환)
    @GetMapping("/weather/api")
    @ResponseBody
    public Map<String, Object> getWeatherJson() {
        double lat = 37.5665;
        double lon = 126.9780;

        Map<String, Object> data = new HashMap<>();
        try {
            var weather = weatherService.getWeather(lat, lon, "Asia/Seoul").block();

            if (weather == null || weather.getCurrentWeather() == null) {
                data.put("error", "No weather data received");
                return data;
            }

            // 도시 이름
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

            var current = weather.getCurrentWeather();
            
            // 습도
            double[] humidities = weather.getHourly().getRelativeHumidity2m();
            double humidity = humidities != null && humidities.length > 0 ? humidities[0] : -1;

            data.put("city", city);
            data.put("temperature", current.getTemperature());
            data.put("windspeed", current.getWindspeed());
            data.put("weathercode", current.getWeathercode());
           
            data.put("humidity", humidity);


        } catch (Exception e) {
            data.put("error", e.getMessage());
        }

        return data;
    }
}

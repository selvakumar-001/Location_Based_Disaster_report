package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    private static final String OPEN_WEATHER_MAP_API_KEY = "46d6d6e479ee2862e90215d9a1d65495";

    @PostMapping("/get-weather-info")
    public ResponseEntity<String> getWeatherInfo(@RequestBody Coordinates coordinates) {
        double lat = coordinates.getLat();
        double lng = coordinates.getLng();
        try {
            String apiUrl = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%.6f&lon=%.6f&appid=%s",
                    lat, lng, OPEN_WEATHER_MAP_API_KEY);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String weatherResponse = responseEntity.getBody();
                return ResponseEntity.ok(weatherResponse);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}

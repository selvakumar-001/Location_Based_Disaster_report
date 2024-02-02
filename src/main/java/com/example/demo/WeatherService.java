package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_BASE_URL = "https://history.openweathermap.org/data/2.5/history/city";

    public String getHistoricalWeather(double lat, double lon, String apiKey) {
        String apiUrl = String.format("%s?lat=%s&lon=%s&type=hour&appid=%s", API_BASE_URL, lat, lon, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }
}

package com.example.demo;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;


@RestController
@RequestMapping("/weather-history")
public class WeatherHistoryController {

    private static final String OPEN_WEATHER_MAP_API_KEY = "46d6d6e479ee2862e90215d9a1d65495";
    private static final String API_URL = "https://history.openweathermap.org/data/2.5/history/city";

    @GetMapping("/download-json")
    public ResponseEntity<Resource> downloadWeatherHistoryJson(
            @RequestParam(name = "lat", required = true) double lat,
            @RequestParam(name = "lon", required = true) double lon,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end,
            @RequestParam(name = "cnt", required = false) String cnt
    ) {
        try {
            String apiUrl = String.format("%s?lat=%.6f&lon=%.6f&type=hour&start=%s&end=%s&cnt=%s&appid=%s",
                    API_URL, lat, lon, start, end, cnt, OPEN_WEATHER_MAP_API_KEY);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String jsonData = responseEntity.getBody();

                // Save JSON data to a file
                String fileName = "weather-history.json";
                File file = new File(fileName);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(jsonData);
                }

                // Prepare file resource for response
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

                // Set content type as application/json
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
                headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resource);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}

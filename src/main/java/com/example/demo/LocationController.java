// LocationController.java
package com.example.demo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LocationController {

    @PostMapping("/get-location-info")
    public ResponseEntity<String> getLocationInfo(@RequestBody Coordinates coordinates) {
        double lat = coordinates.getLat();
        double lng = coordinates.getLng();
        System.out.print(lng);
        // Perform reverse geocoding using a REST API (OpenCage Geocoding API in this example)
        String apiKey = "9adfdcd3204c4a2ea980738df9b37ae2";
        String apiUrl = String.format("https://api.opencagedata.com/geocode/v1/json?q=%.6f+%.6f&key=%s", lat, lng, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            String location = jsonNode
                    .path("results")
                    .path(0)
                    .path("formatted")
                    .asText();

            return ResponseEntity.ok("{\"location\":\"" + location + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"Failed to get location information\"}");
        }
    }
}

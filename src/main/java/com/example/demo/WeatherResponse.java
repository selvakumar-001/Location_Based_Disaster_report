package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Coord coord;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private Sys sys;
    private String name;

    // Getters and setters

    // Additional getters for specific properties you want to extract
    // Example:
    // public String getTemperature() {
    //     return main != null ? main.getTemp() : null;
    // }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Coord {
    private double lon;
    private double lat;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Main {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Wind {
    private double speed;
    private int deg;
    private double gust;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Rain {
    @JsonProperty("1h")
    private double rain1h;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Clouds {
    private int all;

    // Getters and setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Sys {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;

    // Getters and setters
}

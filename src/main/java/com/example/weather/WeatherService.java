package com.example.weather;

import java.util.Optional;

public interface WeatherService {
    Optional<WeatherData> getWeatherData(double latitude, double longitude, int limit);
}

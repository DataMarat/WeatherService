package com.example.weather;

import java.util.List;

public class WeatherData {
    private final List<Integer> temperatures;

    public WeatherData(List<Integer> temperatures) {
        this.temperatures = temperatures;
    }

    public List<Integer> getTemperatures() {
        return temperatures;
    }

    public double calculateAverageTemperature() {
        return temperatures.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }
}

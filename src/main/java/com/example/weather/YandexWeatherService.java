package com.example.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YandexWeatherService implements WeatherService {
    private static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String apiKey;

    public YandexWeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Optional<WeatherData> getWeatherData(double latitude, double longitude, int limit) {
        try {
            URI uri = new URI(String.format("%s?lat=%.2f&lon=%.2f&limit=%d", API_URL, latitude, longitude, limit));
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Yandex-API-Key", apiKey);

            if (conn.getResponseCode() != 200) {
                System.err.println("Failed to connect to Yandex API: " + conn.getResponseCode());
                return Optional.empty();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Полный JSON-ответ
            String jsonResponseString = response.toString();
            JSONObject jsonResponse = new JSONObject(jsonResponseString);

            // Выводим весь ответ JSON в формате строки (для требований задания)
            System.out.println("Полный JSON-ответ от сервиса:");
            System.out.println(jsonResponseString);

            // Извлечение текущей температуры в объекте fact {temp}
            int currentTemp = jsonResponse.getJSONObject("fact").getInt("temp");
            String currentTemperature = "Текущая температура (fact {temp}): " + currentTemp + "°C";
            System.out.println(currentTemperature);

            // Получение температур за несколько дней
            JSONArray forecasts = jsonResponse.getJSONArray("forecasts");
            List<Integer> temperatures = new ArrayList<>();
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject day = forecasts.getJSONObject(i);
                int temp = day.getJSONObject("parts").getJSONObject("day").getInt("temp_avg");
                temperatures.add(temp);
            }

            return Optional.of(new WeatherData(temperatures));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

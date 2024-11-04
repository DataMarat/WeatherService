package com.example.weather;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class WeatherApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Шаг 1: Запрос ключа API Яндекс.Погоды у пользователя
        System.out.print("Введите ваш API ключ для Яндекс.Погоды: ");
        String apiKey = scanner.nextLine();

        if (apiKey.isEmpty()) {
            System.out.println("Ключ API не может быть пустым.");
            return;
        }

        YandexWeatherService weatherService = new YandexWeatherService(apiKey);
        IpService ipService = new IpService(); // Создаем экземпляр IpService
        LocationService locationService = new IpLocationService(ipService); // Передаем IpService в IpLocationService

        // Шаг 2: Выбор способа получения координат
        double latitude;
        double longitude;
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Выберите способ получения координат:");
            System.out.println("1. Определить координаты по IP-адресу");
            System.out.println("2. Ввести координаты вручную");
            System.out.print("Ваш выбор (1 или 2): ");
            try {
                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    validChoice = true;
                } else {
                    System.out.println("Некорректный выбор. Пожалуйста, введите 1 или 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: пожалуйста, введите целое число 1 или 2.");
                scanner.next();
            }
        }

        if (choice == 1) {
            // Получаем текущие координаты пользователя через LocationService
            double[] coordinates = locationService.getCoordinates();
            if (coordinates == null) {
                System.out.println("Не удалось определить координаты по IP.");
                return;
            }
            latitude = coordinates[0];
            longitude = coordinates[1];
        } else if (choice == 2) {
            // Запрос координат у пользователя - долготу и широту
            System.out.print("Введите долготу (от -180 до 180): ");
            longitude = scanner.nextDouble();
            if (!CoordinatesValidator.isValidLongitude(longitude)) {
                System.out.println("Некорректное значение долготы. Значение должно быть от -180 до 180.");
                return;
            }

            System.out.print("Введите широту (от -90 до 90): ");
            latitude = scanner.nextDouble();
            if (!CoordinatesValidator.isValidLatitude(latitude)) {
                System.out.println("Некорректное значение широты. Значение должно быть от -90 до 90.");
                return;
            }
        } else {
            System.out.println("Некорректный выбор. Завершение программы.");
            return;
        }

        // Шаг 3: Запрос данных о погоде
        int limit = 5;
        Optional<WeatherData> weatherDataOptional = weatherService.getWeatherData(latitude, longitude, limit);

        if (weatherDataOptional.isPresent()) {
            WeatherData weatherData = weatherDataOptional.get();
            String lastDaysTemperatures = "Значения температуры за последние дни, °C: " + weatherData.getTemperatures();
            System.out.println(lastDaysTemperatures);

            String averageTemperature = "Средняя температура: " + weatherData.calculateAverageTemperature() + "°C";
            System.out.println(averageTemperature);
        } else {
            System.out.println("Не удалось получить данные о погоде.");
        }

        scanner.close();
    }
}

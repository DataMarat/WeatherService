package com.example.weather;

public class CoordinatesValidator {

    /**
     * Проверяет корректность широты.
     *
     * @param latitude широта
     * @return true, если широта находится в пределах от -90 до 90, иначе false
     */
    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    /**
     * Проверяет корректности долготы.
     *
     * @param longitude долгота
     * @return true, если долгота находится в пределах от -180 до 180, иначе false
     */
    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }
}

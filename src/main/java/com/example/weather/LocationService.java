package com.example.weather;

public interface LocationService {
    /**
     * Определяет координаты текущего пользователя.
     *
     * @return массив из двух элементов: широта и долгота, или null, если определить координаты не удалось
     */
    double[] getCoordinates();
}
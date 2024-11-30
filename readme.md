# WeatherService

## Описание
WeatherService — это консольное приложение на Java, которое получает данные о погоде с сервиса Яндекс.Погода. Приложение также может автоматически определять координаты пользователя по его публичному IP с помощью сервиса IP-геолокации.

## Функционал
- Получение координат:
    - Определение координат по IP.
    - Ручной ввод координат.
- Запрос прогноза погоды на основе полученных координат.
- Вывод полного ответа от сервиса и текущей температуры.

## Установка и запуск

### 1. Клонирование репозитория
Склонируйте проект из GitHub на ваш компьютер:
```bash
git clone https://github.com/DataMarat/WeatherService.git
cd WeatherService
```

### 2. Запуск проекта
main-класс находится в WeatherApplication.java.
Потребуется ввести ключ от Яндекс.Погоды и выбрать режим определения координат.

## TODO
Добавить резервный эндпоинт https://api.open-meteo.com/v1/forecast
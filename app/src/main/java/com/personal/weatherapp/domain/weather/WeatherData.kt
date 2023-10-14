package com.personal.weatherapp.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: WeatherWindDirection,
    val humidity: Int,
    val humidityType: WeatherHumidity,
    val weatherType: WeatherType
)

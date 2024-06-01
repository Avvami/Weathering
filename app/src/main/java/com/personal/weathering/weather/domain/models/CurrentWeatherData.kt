package com.personal.weathering.weather.domain.models

import java.time.LocalDateTime

data class CurrentWeatherData(
    val time: LocalDateTime,
    val temperature: Double,
    val apparentTemperature: Double,
    val humidity: Int,
    val humidityType: HumidityType,
    val weatherType: WeatherType,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Float,
    val windDirectionType: WindDirectionType
)

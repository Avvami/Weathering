package com.personal.weathering.domain.models.weather

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyWeatherData(
    val time: LocalDate,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val weatherType: WeatherType,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime
)

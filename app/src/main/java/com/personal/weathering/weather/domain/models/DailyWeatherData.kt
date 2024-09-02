package com.personal.weathering.weather.domain.models

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class DailyWeatherData(
    val uniqueKey: String = UUID.randomUUID().toString(),
    val time: LocalDate?,
    val temperatureMax: Double?,
    val temperatureMin: Double?,
    val weatherType: WeatherType,
    val windSpeedMax: Double?,
    val dominantWindDirection: Float?,
    val dominantWindDirectionType: WindDirectionType,
    val daylightDuration: Duration?,
    val sunrise: LocalDateTime?,
    val sunset: LocalDateTime?
)

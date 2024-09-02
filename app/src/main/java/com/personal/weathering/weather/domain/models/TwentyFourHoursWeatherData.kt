package com.personal.weathering.weather.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class TwentyFourHoursWeatherData(
    val uniqueKey: String = UUID.randomUUID().toString(),
    val time: LocalDateTime?,
    val temperature: Double?,
    val weatherType: WeatherType,
    val sunrise: LocalDateTime? = null,
    val sunset: LocalDateTime? = null
)

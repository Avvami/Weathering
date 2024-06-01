package com.personal.weathering.weather.domain.models

import java.time.LocalDateTime

data class TwentyFourHoursWeatherData(
    val time: LocalDateTime,
    val temperature: Double,
    val weatherType: WeatherType,
    val sunrise: LocalDateTime? = null,
    val sunset: LocalDateTime? = null
)

package com.personal.weathering.domain.models.weather

import java.time.LocalDateTime

data class TwentyFourHoursWeatherData(
    val time: LocalDateTime,
    val temperature: Int,
    val weatherType: WeatherType
)
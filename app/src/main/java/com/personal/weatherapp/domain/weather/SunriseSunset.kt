package com.personal.weatherapp.domain.weather

import java.time.LocalDate
import java.time.LocalDateTime

data class SunriseSunset(
    val time: LocalDate,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime
)

package com.personal.weathering.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun convertStringToDateTime(dateString: String?): LocalDateTime? {
    return try {
        dateString?.let { string ->
            LocalDateTime.parse(string, DateTimeFormatter.ISO_DATE_TIME)
        }
    } catch (e: Exception) {
        null
    }
}

fun convertStringToDate(dateString: String?): LocalDate? {
    return try {
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
        }
    } catch (e: Exception) {
        null
    }
}

fun convertToMetersPerSecond(speedKmPerHour: Double): Double = speedKmPerHour / 3.6

fun convertToMmHg(speedKmPerHour: Double): Double = speedKmPerHour / 3.6

fun convertToFahrenheit(celsius: Double): Double = celsius * 9.0 / 5.0 + 32
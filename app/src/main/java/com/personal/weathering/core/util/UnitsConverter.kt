package com.personal.weathering.core.util

object UnitsConverter {

    fun toMetersPerSecond(speedKmPerHour: Double): Double = speedKmPerHour / 3.6

    fun toFahrenheit(celsius: Double): Double = celsius * 9.0 / 5.0 + 32

    fun toMmHg(hPa: Double): Double = hPa * 0.75006156
}
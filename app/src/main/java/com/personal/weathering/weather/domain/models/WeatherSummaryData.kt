package com.personal.weathering.weather.domain.models

data class WeatherSummaryData(
    val temperature: Double?,
    val apparentTemperature: Double?,
    val humidity: Int?,
    val humidityType: HumidityType?,
    val weatherType: WeatherType?,
    val pressure: Double?,
    val windSpeed: Double?,
    val windDirection: Float?,
    val windDirectionType: WindDirectionType
)

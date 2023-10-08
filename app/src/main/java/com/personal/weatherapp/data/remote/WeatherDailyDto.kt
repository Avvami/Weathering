package com.personal.weatherapp.data.remote

data class WeatherDailyDto(
    val time: List<String>,
    val sunrise: List<String>,
    val sunset: List<String>
)

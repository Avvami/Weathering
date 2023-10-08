package com.personal.weatherapp.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val weatherDataPerWeek: MutableMap<Int, List<WeatherData>>?,
    val currentWeatherData: WeatherData?,
    val weatherDaily: WeatherDaily?
)

package com.personal.weatherapp.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val dailyWeatherData: List<List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val sunriseSunset: SunriseSunset?
)

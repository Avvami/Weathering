package com.personal.weatherapp.domain.repository

import com.personal.weatherapp.domain.util.Resource
import com.personal.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
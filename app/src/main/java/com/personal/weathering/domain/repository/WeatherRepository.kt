package com.personal.weathering.domain.repository

import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
package com.personal.weathering.weather.domain.repository

import com.personal.weathering.core.util.Resource
import com.personal.weathering.weather.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}
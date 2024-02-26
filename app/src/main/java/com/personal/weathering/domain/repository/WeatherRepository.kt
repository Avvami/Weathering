package com.personal.weathering.domain.repository

import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.models.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}
package com.personal.weathering.data.repository

import com.personal.weathering.data.mappers.toWeatherInfo
import com.personal.weathering.data.remote.WeatherApi
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.models.weather.WeatherInfo

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = weatherApi.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
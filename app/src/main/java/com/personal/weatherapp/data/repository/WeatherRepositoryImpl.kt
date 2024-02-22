package com.personal.weatherapp.data.repository

import com.personal.weatherapp.data.mappers.toWeatherInfo
import com.personal.weatherapp.data.remote.WeatherApi
import com.personal.weatherapp.domain.repository.WeatherRepository
import com.personal.weatherapp.domain.util.Resource
import com.personal.weatherapp.domain.weather.WeatherInfo

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
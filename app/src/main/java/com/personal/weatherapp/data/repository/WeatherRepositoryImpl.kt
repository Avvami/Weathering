package com.personal.weatherapp.data.repository

import com.personal.weatherapp.data.mappers.toAQInfo
import com.personal.weatherapp.data.mappers.toWeatherInfo
import com.personal.weatherapp.data.remote.AQApi
import com.personal.weatherapp.data.remote.WeatherApi
import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.repository.WeatherRepository
import com.personal.weatherapp.domain.util.Resource
import com.personal.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val aqApi: AQApi
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

    override suspend fun getAQData(lat: Double, long: Double): Resource<AQInfo> {
        return try {
            Resource.Success(
                data = aqApi.getAQData(
                    lat = lat,
                    long = long
                ).toAQInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
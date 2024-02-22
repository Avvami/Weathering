package com.personal.weatherapp.data.repository

import com.personal.weatherapp.data.mappers.toAQInfo
import com.personal.weatherapp.data.remote.AQApi
import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.repository.AqRepository
import com.personal.weatherapp.domain.util.Resource

class AqRepositoryImpl(
    private val aqApi: AQApi
): AqRepository {
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
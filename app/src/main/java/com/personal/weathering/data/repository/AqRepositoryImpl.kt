package com.personal.weathering.data.repository

import com.personal.weathering.data.mappers.toAQInfo
import com.personal.weathering.data.remote.AQApi
import com.personal.weathering.domain.airquality.AQInfo
import com.personal.weathering.domain.repository.AqRepository
import com.personal.weathering.domain.util.Resource

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
package com.personal.weathering.domain.repository

import com.personal.weathering.domain.airquality.AQInfo
import com.personal.weathering.domain.util.Resource

interface AqRepository {
    suspend fun getAQData(lat: Double, long: Double): Resource<AQInfo>
}
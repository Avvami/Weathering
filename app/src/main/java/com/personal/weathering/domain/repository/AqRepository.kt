package com.personal.weathering.domain.repository

import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.util.Resource

interface AqRepository {
    suspend fun getAqData(lat: Double, lon: Double): Resource<AqInfo>
}
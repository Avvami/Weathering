package com.personal.weathering.aq.domain.repository

import com.personal.weathering.aq.domain.models.AqInfo
import com.personal.weathering.core.util.Resource

interface AqRepository {
    suspend fun getAqData(lat: Double, lon: Double): Resource<AqInfo>
}
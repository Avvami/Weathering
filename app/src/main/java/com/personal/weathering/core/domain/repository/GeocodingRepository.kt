package com.personal.weathering.core.domain.repository

import com.personal.weathering.core.domain.models.GeocodingInfo
import com.personal.weathering.core.util.Resource

interface GeocodingRepository {
    suspend fun getCurrentLocation(lat: Double, lon: Double): Resource<GeocodingInfo>
}
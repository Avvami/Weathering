package com.personal.weathering.domain.repository

import com.personal.weathering.domain.models.geocoding.GeocodingInfo
import com.personal.weathering.domain.util.Resource

interface GeocodingRepository {
    suspend fun getCurrentLocation(lat: Double, lon: Double): Resource<GeocodingInfo>
}
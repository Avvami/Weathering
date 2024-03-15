package com.personal.weathering.domain.location

import com.personal.weathering.domain.models.location.LocationInfo
import com.personal.weathering.domain.util.Resource

interface LocationClient {
    suspend fun getCurrentLocation(): Resource<LocationInfo>
}
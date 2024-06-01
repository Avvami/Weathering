package com.personal.weathering.core.domain.location

import com.personal.weathering.core.domain.models.LocationInfo
import com.personal.weathering.core.util.Resource

interface LocationClient {
    suspend fun getCurrentLocation(): Resource<LocationInfo>
}
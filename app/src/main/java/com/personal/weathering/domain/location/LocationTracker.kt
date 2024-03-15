package com.personal.weathering.domain.location

import android.location.Location
import com.personal.weathering.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation(): Resource<Location>
}
package com.personal.weathering.core.data.mappers

import android.location.Location
import com.personal.weathering.core.domain.models.LocationInfo

fun Location.toLocationInfo(): LocationInfo {
    return LocationInfo(
        this.latitude,
        this.longitude
    )
}
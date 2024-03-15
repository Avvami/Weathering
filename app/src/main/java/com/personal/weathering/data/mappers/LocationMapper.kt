package com.personal.weathering.data.mappers

import android.location.Location
import com.personal.weathering.domain.models.location.LocationInfo

fun Location.toLocationInfo(): LocationInfo {
    return LocationInfo(
        this.latitude,
        this.longitude
    )
}
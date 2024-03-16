package com.personal.weathering.data.mappers

import com.personal.weathering.data.models.GeocodingDto
import com.personal.weathering.domain.models.geocoding.GeocodingInfo

fun GeocodingDto.toGeocodingInfo(): GeocodingInfo {
    return GeocodingInfo(
        error = error,
        city = address?.city,
        town = address?.town,
        village = address?.village,
        hamlet = address?.hamlet,
        municipality = address?.municipality
    )
}
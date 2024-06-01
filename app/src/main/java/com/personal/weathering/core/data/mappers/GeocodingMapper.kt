package com.personal.weathering.core.data.mappers

import com.personal.weathering.core.data.models.GeocodingDto
import com.personal.weathering.core.domain.models.GeocodingInfo

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
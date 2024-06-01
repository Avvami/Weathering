package com.personal.weathering.core.domain.models

data class GeocodingInfo(
    val error: String?,
    val city: String?,
    val town: String?,
    val village: String?,
    val hamlet: String?,
    val municipality: String?
)

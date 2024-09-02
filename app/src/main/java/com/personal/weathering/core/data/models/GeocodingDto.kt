package com.personal.weathering.core.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodingDto(
    @Json(name = "address")
    val address: AddressDto?,
    @Json(name = "error")
    val error: String?
)

@JsonClass(generateAdapter = true)
data class AddressDto(
    @Json(name = "city")
    val city: String?,
    @Json(name = "town")
    val town: String?,
    @Json(name = "village")
    val village: String?,
    @Json(name = "hamlet")
    val hamlet: String?,
    @Json(name = "municipality")
    val municipality: String?
)

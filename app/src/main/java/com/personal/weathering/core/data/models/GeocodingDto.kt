package com.personal.weathering.core.data.models

import com.squareup.moshi.Json

data class GeocodingDto(
    @field:Json(name = "address")
    val address: AddressDto?,
    @field:Json(name = "error")
    val error: String
)

data class AddressDto(
    @field:Json(name = "city")
    val city: String?,
    @field:Json(name = "town")
    val town: String?,
    @field:Json(name = "village")
    val village: String?,
    @field:Json(name = "hamlet")
    val hamlet: String?,
    @field:Json(name = "municipality")
    val municipality: String?
)

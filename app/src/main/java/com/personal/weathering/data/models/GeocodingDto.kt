package com.personal.weathering.data.models

import com.squareup.moshi.Json

data class GeocodingDto(
    @field:Json(name = "address")
    val address: AddressDto?,
    @field:Json(name = "error")
    val error: String
)

data class AddressDto(
    val city: String?,
    val town: String?,
    val village: String?,
    val hamlet: String?,
    val municipality: String?
)

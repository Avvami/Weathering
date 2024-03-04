package com.personal.weathering.data.models

import com.squareup.moshi.Json

data class SearchDto(
    @field:Json(name = "results")
    val searchResults: List<SearchResultDto>?
)

data class SearchResultDto(
    val id: Int,
    val name: String,
    @field:Json(name = "latitude")
    val lat: Double,
    @field:Json(name = "longitude")
    val lon: Double,
    val country: String?,
    @field:Json(name = "country_code")
    val countryCode: String?,
    @field:Json(name = "admin1")
    val adminLevel: String?,
)

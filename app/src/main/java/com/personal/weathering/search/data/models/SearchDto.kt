package com.personal.weathering.search.data.models

import com.squareup.moshi.Json

data class SearchDto(
    @field:Json(name = "results")
    val searchResults: List<SearchResultDto>?
)

data class SearchResultDto(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "latitude")
    val lat: Double,
    @field:Json(name = "longitude")
    val lon: Double,
    @field:Json(name = "country")
    val country: String?,
    @field:Json(name = "country_code")
    val countryCode: String?,
    @field:Json(name = "admin1")
    val adminLevel: String?,
)

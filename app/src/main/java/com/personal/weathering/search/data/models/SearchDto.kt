package com.personal.weathering.search.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchDto(
    @Json(name = "results")
    val searchResults: List<SearchResultDto>?
)

@JsonClass(generateAdapter = true)
data class SearchResultDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "latitude")
    val lat: Double,
    @Json(name = "longitude")
    val lon: Double,
    @Json(name = "country")
    val country: String?,
    @Json(name = "country_code")
    val countryCode: String?,
    @Json(name = "admin1")
    val adminLevel: String?,
)

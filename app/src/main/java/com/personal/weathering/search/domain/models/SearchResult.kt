package com.personal.weathering.search.domain.models

data class SearchResult(
    val cityId: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String?,
    val countryCode: String?,
    val adminLevel: String?,
)

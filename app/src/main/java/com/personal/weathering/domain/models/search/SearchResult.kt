package com.personal.weathering.domain.models.search

data class SearchResult(
    val city: String,
    val lat: Double,
    val lon: Double,
    val countryCode: String,
    val adminLevel: String?,
)

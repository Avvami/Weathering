package com.personal.weathering.domain.models.search

data class SearchResult(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String?,
    val countryCode: String?,
    val adminLevel: String?,
)

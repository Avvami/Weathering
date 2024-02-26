package com.personal.weathering.domain.models.search

data class SearchResult(
    val city: String,
    val lat: String,
    val lon: String,
    val countryCode: String,
    val adminLevel: String,
)

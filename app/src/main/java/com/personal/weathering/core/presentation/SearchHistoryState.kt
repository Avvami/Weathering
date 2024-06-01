package com.personal.weathering.core.presentation

data class SearchHistoryState(
    val id: Int,
    val cityId: Int,
    val city: String,
    val lat: Double,
    val lon: Double
)

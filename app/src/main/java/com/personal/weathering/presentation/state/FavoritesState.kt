package com.personal.weathering.presentation.state

data class FavoritesState(
    val id: Int,
    val city: String,
    val lat: Double,
    val lon: Double
)

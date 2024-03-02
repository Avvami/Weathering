package com.personal.weathering.presentation.state

import com.personal.weathering.data.local.FavoriteEntity

data class PreferencesState(
    val currentCity: String = "London",
    val lat: Double = 51.50853,
    val lon: Double = -0.12574,
    val searchLanguageCode: String = "en",
    val useCelsius: Boolean = true,
    val useKmPerHour: Boolean = true,
    val useHpa: Boolean = true,
    val useUSaq: Boolean = true,
    val favorites: List<FavoriteEntity> = listOf()
)

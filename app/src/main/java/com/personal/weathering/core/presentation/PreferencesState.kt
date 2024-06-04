package com.personal.weathering.core.presentation

data class PreferencesState(
    val cityId: Int = 2643743,
    val selectedCity: String = "London",
    val selectedCityLat: Double = 51.50853,
    val selectedCityLon: Double = -0.12574,
    val useLocation: Boolean = false,
    val currentLocationCity: String = "London",
    val currentLocationLat: Double = 51.50853,
    val currentLocationLon: Double = -0.12574,
    val searchLanguageCode: String = "en",
    val isDark: Boolean = false,
    val useCelsius: Boolean = true,
    val useKmPerHour: Boolean = true,
    val useHpa: Boolean = true,
    val useUSaq: Boolean = true,
    val use12hour: Boolean = true
)

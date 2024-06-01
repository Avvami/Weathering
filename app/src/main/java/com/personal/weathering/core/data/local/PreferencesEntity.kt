package com.personal.weathering.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesEntity(
    @PrimaryKey val id: Int = 0,
    val cityId: Int,
    val selectedCity: String,
    val selectedCityLat: Double,
    val selectedCityLon: Double,
    val useLocation: Boolean,
    val currentLocationCity: String,
    val currentLocationLat: Double,
    val currentLocationLon: Double,
    val searchLanguageCode: String,
    val isDark: Boolean,
    val useCelsius: Boolean,
    val useKmPerHour: Boolean,
    val useHpa: Boolean,
    val useUSaq: Boolean
)

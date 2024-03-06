package com.personal.weathering.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesEntity(
    @PrimaryKey val id: Int = 0,
    val cityId: Int,
    val currentCity: String,
    val lat: Double,
    val lon: Double,
    val searchLanguageCode: String,
    val isDark: Boolean,
    val useLocation: Boolean,
    val useCelsius: Boolean,
    val useKmPerHour: Boolean,
    val useHpa: Boolean,
    val useUSaq: Boolean
)

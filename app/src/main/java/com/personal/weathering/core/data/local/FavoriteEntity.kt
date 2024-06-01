package com.personal.weathering.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityId: Int,
    val city: String,
    val lat: Double,
    val lon: Double
)

package com.personal.weathering.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val city: String,
    val lat: Double,
    val lon: Double
)

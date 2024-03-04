package com.personal.weathering.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["cityId"], unique = true)])
data class SearchResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityId: Int,
    val city: String,
    val lat: Double,
    val lon: Double
)

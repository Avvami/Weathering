package com.personal.weatherapp.data.remote

import com.squareup.moshi.Json

data class AQDto(
    @field:Json(name = "hourly")
    val aqData: AQDataDto
)
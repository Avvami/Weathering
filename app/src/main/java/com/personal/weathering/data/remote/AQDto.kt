package com.personal.weathering.data.remote

import com.squareup.moshi.Json

data class AQDto(
    @field:Json(name = "hourly")
    val aqData: AQDataDto
)
package com.personal.weatherapp.data.remote

import com.squareup.moshi.Json

data class AQDataDto(
    val time: List<String>,
    @field:Json(name = "pm10")
    val particulateMatters10: List<Double>,
    @field:Json(name = "pm2_5")
    val particulateMatters25: List<Double>,
    @field:Json(name = "carbon_monoxide")
    val carbonMonoxides: List<Double>,
    @field:Json(name = "nitrogen_dioxide")
    val nitrogenDioxides: List<Double>,
    @field:Json(name = "sulphur_dioxide")
    val sulphurDioxides: List<Double>,
    @field:Json(name = "us_aqi")
    val airQualityUS: List<Int>
)

package com.personal.weathering.data.models

import com.squareup.moshi.Json

data class AqDto(
    @field:Json(name = "current")
    val currentAq: CurrentAqDto,
    @field:Json(name = "hourly")
    val hourlyAq: HourlyAqDto
)

data class CurrentAqDto(
    val time: String,
    @field:Json(name = "european_aqi")
    val europeanAqi: Int,
    @field:Json(name = "us_aqi")
    val usAqi: Int,
    @field:Json(name = "pm10")
    val particulateMatter10: Double,
    @field:Json(name = "pm2_5")
    val particulateMatter25: Double,
    @field:Json(name = "carbon_monoxide")
    val carbonMonoxide: Double,
    @field:Json(name = "nitrogen_dioxide")
    val nitrogenDioxide: Double,
    @field:Json(name = "sulphur_dioxide")
    val sulphurDioxide: Double,
    val ozone: Double
)

data class HourlyAqDto(
    val time: List<String>,
    @field:Json(name = "european_aqi")
    val europeanAqies: List<Int>,
    @field:Json(name = "us_aqi")
    val usAqies: List<Int>,
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
    @field:Json(name = "ozone")
    val ozones: List<Double>
)
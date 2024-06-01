package com.personal.weathering.aq.data.models

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
    val euAqi: Int,
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
    val euAqies: List<Int>,
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
    val ozones: List<Double>,
    @field:Json(name = "us_aqi_pm10")
    val usAqiParticulateMatters10: List<Int>,
    @field:Json(name = "us_aqi_pm2_5")
    val usAqiParticulateMatters25: List<Int>,
    @field:Json(name = "us_aqi_carbon_monoxide")
    val usAqiCarbonMonoxides: List<Int>,
    @field:Json(name = "us_aqi_nitrogen_dioxide")
    val usAqiNitrogenDioxides: List<Int>,
    @field:Json(name = "us_aqi_sulphur_dioxide")
    val usAqiSulphurDioxides: List<Int>,
    @field:Json(name = "us_aqi_ozone")
    val usAqiOzones: List<Int>,
    @field:Json(name = "european_aqi_pm10")
    val euAqiParticulateMatters10: List<Int>,
    @field:Json(name = "european_aqi_pm2_5")
    val euAqiParticulateMatters25: List<Int>,
    @field:Json(name = "european_aqi_nitrogen_dioxide")
    val euAqiNitrogenDioxides: List<Int>,
    @field:Json(name = "european_aqi_sulphur_dioxide")
    val euAqiSulphurDioxides: List<Int>,
    @field:Json(name = "european_aqi_ozone")
    val euAqiOzones: List<Int>
)
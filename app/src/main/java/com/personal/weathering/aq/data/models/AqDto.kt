package com.personal.weathering.aq.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AqDto(
    @Json(name = "current")
    val currentAq: CurrentAqDto,
    @Json(name = "hourly")
    val hourlyAq: HourlyAqDto
)

@JsonClass(generateAdapter = true)
data class CurrentAqDto(
    val time: String,
    @Json(name = "european_aqi")
    val euAqi: Int,
    @Json(name = "us_aqi")
    val usAqi: Int,
    @Json(name = "pm10")
    val particulateMatter10: Double,
    @Json(name = "pm2_5")
    val particulateMatter25: Double,
    @Json(name = "carbon_monoxide")
    val carbonMonoxide: Double,
    @Json(name = "nitrogen_dioxide")
    val nitrogenDioxide: Double,
    @Json(name = "sulphur_dioxide")
    val sulphurDioxide: Double,
    val ozone: Double
)

@JsonClass(generateAdapter = true)
data class HourlyAqDto(
    val time: List<String>,
    @Json(name = "european_aqi")
    val euAqies: List<Int>,
    @Json(name = "us_aqi")
    val usAqies: List<Int>,
    @Json(name = "pm10")
    val particulateMatters10: List<Double>,
    @Json(name = "pm2_5")
    val particulateMatters25: List<Double>,
    @Json(name = "carbon_monoxide")
    val carbonMonoxides: List<Double>,
    @Json(name = "nitrogen_dioxide")
    val nitrogenDioxides: List<Double>,
    @Json(name = "sulphur_dioxide")
    val sulphurDioxides: List<Double>,
    @Json(name = "ozone")
    val ozones: List<Double>,
    @Json(name = "us_aqi_pm10")
    val usAqiParticulateMatters10: List<Int>,
    @Json(name = "us_aqi_pm2_5")
    val usAqiParticulateMatters25: List<Int>,
    @Json(name = "us_aqi_carbon_monoxide")
    val usAqiCarbonMonoxides: List<Int>,
    @Json(name = "us_aqi_nitrogen_dioxide")
    val usAqiNitrogenDioxides: List<Int>,
    @Json(name = "us_aqi_sulphur_dioxide")
    val usAqiSulphurDioxides: List<Int>,
    @Json(name = "us_aqi_ozone")
    val usAqiOzones: List<Int>,
    @Json(name = "european_aqi_pm10")
    val euAqiParticulateMatters10: List<Int>,
    @Json(name = "european_aqi_pm2_5")
    val euAqiParticulateMatters25: List<Int>,
    @Json(name = "european_aqi_nitrogen_dioxide")
    val euAqiNitrogenDioxides: List<Int>,
    @Json(name = "european_aqi_sulphur_dioxide")
    val euAqiSulphurDioxides: List<Int>,
    @Json(name = "european_aqi_ozone")
    val euAqiOzones: List<Int>
)
package com.personal.weathering.weather.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @Json(name = "utc_offset_seconds")
    val utcOffset: Int,
    @Json(name = "current")
    val currentWeather: CurrentWeatherDto,
    @Json(name = "hourly")
    val hourlyWeather: HourlyWeatherDto,
    @Json(name = "daily")
    val dailyWeather: DailyWeatherDto
)

@JsonClass(generateAdapter = true)
data class CurrentWeatherDto(
    @Json(name = "time")
    val time: String?,
    @Json(name = "temperature_2m")
    val temperature: Double?,
    @Json(name = "apparent_temperature")
    val apparentTemperature: Double?,
    @Json(name = "relative_humidity_2m")
    val humidity: Int?,
    @Json(name = "weather_code")
    val weatherCode: Int?,
    @Json(name = "pressure_msl")
    val pressure: Double?,
    @Json(name = "wind_speed_10m")
    val windSpeed: Double?,
    @Json(name = "wind_direction_10m")
    val windDirection: Int?,
    @Json(name = "is_day")
    val isDay: Int?
)

@JsonClass(generateAdapter = true)
data class HourlyWeatherDto(
    @Json(name = "time")
    val time: List<String?>,
    @Json(name = "temperature_2m")
    val temperatures: List<Double?>,
    @Json(name = "apparent_temperature")
    val apparentTemperatures: List<Double?>,
    @Json(name = "weather_code")
    val weatherCodes: List<Int?>,
    @Json(name = "pressure_msl")
    val pressures: List<Double?>,
    @Json(name = "wind_speed_10m")
    val windSpeeds: List<Double?>,
    @Json(name = "wind_direction_10m")
    val windDirections: List<Int?>,
    @Json(name = "relative_humidity_2m")
    val humidities: List<Int?>,
    @Json(name = "is_day")
    val isDay: List<Int?>
)

@JsonClass(generateAdapter = true)
data class DailyWeatherDto(
    @Json(name = "time")
    val time: List<String?>,
    @Json(name = "temperature_2m_max")
    val temperaturesMax: List<Double?>,
    @Json(name = "temperature_2m_min")
    val temperaturesMin: List<Double?>,
    @Json(name = "weather_code")
    val weatherCodes: List<Int?>,
    @Json(name = "wind_speed_10m_max")
    val windSpeedsMax: List<Double?>,
    @Json(name = "wind_direction_10m_dominant")
    val dominantWindDirections: List<Int?>,
    @Json(name = "daylight_duration")
    val daylightDurations: List<Double?>,
    @Json(name = "sunrise")
    val sunrises: List<String?>,
    @Json(name = "sunset")
    val sunsets: List<String?>
)
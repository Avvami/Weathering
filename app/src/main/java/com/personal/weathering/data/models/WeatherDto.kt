package com.personal.weathering.data.models

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "utc_offset_seconds")
    val utcOffset: Int,
    @field:Json(name = "current")
    val currentWeather: CurrentWeatherDto,
    @field:Json(name = "hourly")
    val hourlyWeather: HourlyWeatherDto,
    @field:Json(name = "daily")
    val dailyWeather: DailyWeatherDto
)

data class CurrentWeatherDto(
    val time: String,
    @field:Json(name = "temperature_2m")
    val temperature: Double,
    @field:Json(name = "apparent_temperature")
    val apparentTemperature: Double,
    @field:Json(name = "relative_humidity_2m")
    val humidity: Int,
    @field:Json(name = "weather_code")
    val weatherCode: Int,
    @field:Json(name = "pressure_msl")
    val pressure: Double,
    @field:Json(name = "wind_speed_10m")
    val windSpeed: Double,
    @field:Json(name = "wind_direction_10m")
    val windDirection: Int,
    @field:Json(name = "is_day")
    val isDay: Int
)

data class HourlyWeatherDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "apparent_temperature")
    val apparentTemperatures: List<Double>,
    @field:Json(name = "weather_code")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "wind_speed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "wind_direction_10m")
    val windDirections: List<Int>,
    @field:Json(name = "relative_humidity_2m")
    val humidities: List<Int>,
    @field:Json(name = "is_day")
    val isDay: List<Int>
)

data class DailyWeatherDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m_max")
    val temperaturesMax: List<Double>,
    @field:Json(name = "temperature_2m_min")
    val temperaturesMin: List<Double>,
    @field:Json(name = "weather_code")
    val weatherCodes: List<Int>,
    @field:Json(name = "sunrise")
    val sunrises: List<String>,
    @field:Json(name = "sunset")
    val sunsets: List<String>
)
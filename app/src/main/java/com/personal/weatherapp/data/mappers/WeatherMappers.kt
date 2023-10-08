package com.personal.weatherapp.data.mappers

import com.personal.weatherapp.data.remote.WeatherDailyDto
import com.personal.weatherapp.data.remote.WeatherDataDto
import com.personal.weatherapp.data.remote.WeatherDto
import com.personal.weatherapp.domain.weather.WeatherDaily
import com.personal.weatherapp.domain.weather.WeatherData
import com.personal.weatherapp.domain.weather.WeatherInfo
import com.personal.weatherapp.domain.weather.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

private data class IndexedWeatherDaily(
    val index: Int,
    val data: WeatherDaily
)

private fun Int.toBoolean() = this == 1

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        val isDay = isDay[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode, isDay.toBoolean())
            )
        )
    }.groupBy { indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.map { indexedData -> indexedData.data }
    }
}

fun WeatherDailyDto.toWeatherDailyMap(): Map<Int, List<WeatherDaily>> {
    return time.mapIndexed { index, time ->
        val sunrise = sunrise[index]
        val sunset = sunset[index]
        IndexedWeatherDaily(
            index = index,
            data = WeatherDaily(
                time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
                sunrise = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME),
                sunset = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME)
            )
        )
    }.groupBy { indexedData ->
        indexedData.index
    }.mapValues {
        it.value.map { indexedData -> indexedData.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        it.time.hour == when {
            now.minute < 30 -> now.hour
            now.hour == 23 -> 12.00
            else -> now.hour + 1
        }
    }
    val weatherDataPerWeek = mutableMapOf<Int, List<WeatherData>>()
    for (i in 0 until 7) {
        val dayWeatherData = weatherDataMap[i]
        if (dayWeatherData != null) {
            weatherDataPerWeek[i] = dayWeatherData
        }
    }
    val weatherDaily = weatherDaily.toWeatherDailyMap()[0]?.get(0)
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        weatherDataPerWeek = weatherDataPerWeek,
        currentWeatherData = currentWeatherData,
        weatherDaily = weatherDaily
    )
}
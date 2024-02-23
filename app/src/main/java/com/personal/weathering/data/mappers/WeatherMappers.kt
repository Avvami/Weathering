package com.personal.weathering.data.mappers

import com.personal.weathering.data.remote.WeatherDailyDto
import com.personal.weathering.data.remote.WeatherDataDto
import com.personal.weathering.data.remote.WeatherDto
import com.personal.weathering.domain.weather.SunriseSunset
import com.personal.weathering.domain.weather.WeatherData
import com.personal.weathering.domain.weather.WeatherHumidity
import com.personal.weathering.domain.weather.WeatherInfo
import com.personal.weathering.domain.weather.WeatherType
import com.personal.weathering.domain.weather.WeatherWindDirection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

private data class IndexedWeatherDaily(
    val index: Int,
    val data: SunriseSunset
)

private fun Int.toBoolean() = this == 1

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val windDirection = windDirections[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        val isDay = isDay[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure  * 0.75, // mmHg
                windSpeed = windSpeed / 3.6, // m/s
                windDirection = WeatherWindDirection.fromDegree(windDirection),
                humidity = humidity,
                humidityType = WeatherHumidity.fromPercentage(humidity),
                weatherType = WeatherType.fromWMO(weatherCode, isDay.toBoolean())
            )
        )
    }.groupBy { indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.map { indexedData -> indexedData.data }
    }
}

fun WeatherDailyDto.toWeatherDailyMap(): Map<Int, List<SunriseSunset>> {
    return time.mapIndexed { index, time ->
        val sunrise = sunrise[index]
        val sunset = sunset[index]
        IndexedWeatherDaily(
            index = index,
            data = SunriseSunset(
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
    val currentWeatherData = if (now.hour == 23 && now.minute > 30) {
        val tomorrowData = weatherDataMap[1]?.find { it.time.hour == 0 }
        val todayData = weatherDataMap[0]?.find { it.time.hour == now.hour }
        tomorrowData?.let { tomorrow ->
            todayData?.let { today ->
                WeatherData(
                    time = today.time,
                    temperatureCelsius = tomorrow.temperatureCelsius,
                    pressure = tomorrow.pressure,
                    windSpeed = tomorrow.windSpeed,
                    windDirection = tomorrow.windDirection,
                    humidity = tomorrow.humidity,
                    humidityType = tomorrow.humidityType,
                    weatherType = tomorrow.weatherType
                )
            }
        }
    } else {
        weatherDataMap[0]?.find { today ->
            today.time.hour == when {
                now.minute < 30 -> now.hour
                else -> now.hour + 1
            }
        }
    }

    val dailyWeatherData: List<List<WeatherData>> = weatherDataMap.values.toList()
    val sunriseSunset = weatherDaily.toWeatherDailyMap()[0]?.get(0)
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        dailyWeatherData = dailyWeatherData,
        currentWeatherData = currentWeatherData,
        sunriseSunset = sunriseSunset
    )
}
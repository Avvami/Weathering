package com.personal.weathering.data.mappers

import com.personal.weathering.data.models.CurrentWeatherDto
import com.personal.weathering.data.models.DailyWeatherDto
import com.personal.weathering.data.models.HourlyWeatherDto
import com.personal.weathering.data.models.WeatherDto
import com.personal.weathering.domain.models.weather.CurrentWeatherData
import com.personal.weathering.domain.models.weather.DailyWeatherData
import com.personal.weathering.domain.models.weather.HourlyWeatherData
import com.personal.weathering.domain.models.weather.HumidityType
import com.personal.weathering.domain.models.weather.TwentyFourHoursWeatherData
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.models.weather.WeatherType
import com.personal.weathering.domain.models.weather.WindDirectionType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private data class IndexedHourlyWeather(
    val index: Int,
    val data: HourlyWeatherData
)

private fun Int.toBoolean() = this == 1

fun CurrentWeatherDto.toCurrentWeatherData(): CurrentWeatherData {
    return CurrentWeatherData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
        temperature = temperature,
        apparentTemperature = apparentTemperature,
        humidity = humidity,
        humidityType = HumidityType.fromPercentage(humidity),
        weatherType = WeatherType.fromWMO(weatherCode, isDay.toBoolean()),
        pressure = pressure,
        windSpeed = windSpeed,
        windDirection = windDirection.toFloat(),
        windDirectionType = WindDirectionType.fromDegree(windDirection)
    )
}
fun HourlyWeatherDto.toHourlyWeatherData(): Map<Int, List<HourlyWeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val apparentTemperature = apparentTemperatures[index]
        val weatherCode = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val windDirection = windDirections[index]
        val humidity = humidities[index]
        val isDay = isDay[index]
        IndexedHourlyWeather(
            index = index,
            data = HourlyWeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperature,
                apparentTemperature = apparentTemperature,
                humidity = humidity,
                humidityType = HumidityType.fromPercentage(humidity),
                weatherType = WeatherType.fromWMO(weatherCode, isDay.toBoolean()),
                pressure = pressure,
                windSpeed = windSpeed,
                windDirection = windDirection.toFloat(),
                windDirectionType = WindDirectionType.fromDegree(windDirection)
            )
        )
    }.groupBy { indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.map { indexedData -> indexedData.data }
    }
}

fun DailyWeatherDto.toDailyWeatherData(): List<DailyWeatherData> {
    return time.mapIndexed { index, time ->
        val temperatureMax = temperaturesMax[index]
        val temperatureMin = temperaturesMin[index]
        val weatherCode = weatherCodes[index]
        val sunrise = sunrises[index]
        val sunset = sunsets[index]
        DailyWeatherData(
            time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            weatherType = WeatherType.fromWMO(weatherCode, true),
            sunrise = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME),
            sunset = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val zoneOffset = ZoneOffset.ofTotalSeconds(utcOffset)
    val now = LocalDateTime.now(ZoneId.ofOffset("UTC", zoneOffset))
    val flattenHourlyWeatherData = hourlyWeather.toHourlyWeatherData().values.flatten()
    val twentyFourHoursWeatherData = flattenHourlyWeatherData.filter { hourlyData ->
        hourlyData.time.isAfter(now) && hourlyData.time.isBefore(now.plusDays(1))
    }.sortedBy { hourlyData ->
        hourlyData.time
    }.take(24).map { hourlyData ->
        TwentyFourHoursWeatherData(
            time = hourlyData.time,
            temperature = hourlyData.temperature,
            weatherType = hourlyData.weatherType
        )
    }

    return WeatherInfo(
        currentWeatherData = currentWeather.toCurrentWeatherData(),
        twentyFourHoursWeatherData = twentyFourHoursWeatherData,
        hourlyWeatherData = hourlyWeather.toHourlyWeatherData(),
        dailyWeatherData = dailyWeather.toDailyWeatherData()
    )
}
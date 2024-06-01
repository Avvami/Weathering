package com.personal.weathering.weather.data.mappers

import android.content.Context
import com.personal.weathering.R
import com.personal.weathering.weather.data.models.CurrentWeatherDto
import com.personal.weathering.weather.data.models.DailyWeatherDto
import com.personal.weathering.weather.data.models.HourlyWeatherDto
import com.personal.weathering.weather.data.models.WeatherDto
import com.personal.weathering.weather.domain.models.CurrentWeatherData
import com.personal.weathering.weather.domain.models.DailyWeatherData
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import com.personal.weathering.weather.domain.models.HourlyWeatherData
import com.personal.weathering.weather.domain.models.HumidityType
import com.personal.weathering.weather.domain.models.TwentyFourHoursWeatherData
import com.personal.weathering.weather.domain.models.WeatherInfo
import com.personal.weathering.weather.domain.models.WeatherSummaryData
import com.personal.weathering.weather.domain.models.WeatherType
import com.personal.weathering.weather.domain.models.WindDirectionType
import com.personal.weathering.core.util.UiText
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

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
        val windSpeedMax = windSpeedsMax[index]
        val dominantWindDirection = dominantWindDirections[index]
        val daylightDuration = daylightDurations[index]
        val sunrise = sunrises[index]
        val sunset = sunsets[index]
        DailyWeatherData(
            time = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            weatherType = WeatherType.fromWMO(weatherCode, true),
            windSpeedMax = windSpeedMax,
            dominantWindDirection = dominantWindDirection.toFloat(),
            dominantWindDirectionType = WindDirectionType.fromDegree(dominantWindDirection),
            daylightDuration = Duration.ofMillis((daylightDuration * 1000).toLong()),
            sunrise = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME),
            sunset = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}

private fun Map<Int, List<HourlyWeatherData>>.toWeatherByTimePeriods(context: Context): Map<Int, List<DailyWeatherSummaryData>> {
    val timeRanges = mapOf(
        UiText.StringResource(R.string.morning).asString(context) to (6..11),
        UiText.StringResource(R.string.day).asString(context) to (12..17),
        UiText.StringResource(R.string.evening).asString(context) to (18..23),
        UiText.StringResource(R.string.night).asString(context) to (0..5)
    )
    return this.mapValues { (_, hourlyData) ->
        timeRanges.map { (rangeName, timeRange) ->
            val periodWeatherData = hourlyData.filter { it.time.hour in timeRange }
            DailyWeatherSummaryData(
                period = rangeName,
                weatherSummary = periodWeatherData.toOverallWeather()
            )
        }
    }
}

private fun List<HourlyWeatherData>.toOverallWeather(): WeatherSummaryData {
    return WeatherSummaryData(
        temperature = averageBy { it.temperature },
        apparentTemperature = averageBy { it.apparentTemperature },
        humidity = averageBy { it.humidity.toDouble() }.roundToInt(),
        humidityType = mostCommonBy { it.humidityType },
        weatherType = mostCommonBy { it.weatherType },
        pressure = averageBy { it.pressure },
        windSpeed = averageBy { it.windSpeed },
        windDirection = averageBy { it.windDirection.toDouble() }.toFloat(),
        windDirectionType = mostCommonBy { it.windDirectionType }
    )
}

private fun List<HourlyWeatherData>.averageBy(selector: (HourlyWeatherData) -> Double): Double {
    return map(selector).average()
}

private fun <T> List<HourlyWeatherData>.mostCommonBy(selector: (HourlyWeatherData) -> T): T {
    return groupingBy(selector).eachCount().maxBy { it.value }.key
}

fun WeatherDto.toWeatherInfo(context: Context): WeatherInfo {
    val now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(utcOffset)))
    val hourlyWeatherData = hourlyWeather.toHourlyWeatherData()
    val flattenHourlyWeatherData = hourlyWeatherData.values.flatten()

    val twentyFourHoursWeatherData = flattenHourlyWeatherData.asSequence().filter { hourlyData ->
        hourlyData.time.isAfter(now) && hourlyData.time.isBefore(now.plusDays(1))
    }.sortedBy { it.time }.take(24).map { hourlyData ->
        TwentyFourHoursWeatherData(
            time = hourlyData.time,
            temperature = hourlyData.temperature,
            weatherType = hourlyData.weatherType
        )
    }.toMutableList()

    val dailyWeatherData = dailyWeather.toDailyWeatherData()
    dailyWeatherData.find { it.sunrise.isAfter(now) }?.let { sunriseData ->
        twentyFourHoursWeatherData.add(
            TwentyFourHoursWeatherData(
                time = sunriseData.sunrise,
                temperature = 0.0,
                weatherType = WeatherType.fromWMO(0, false),
                sunrise = sunriseData.sunrise
            )
        )
    }
    dailyWeatherData.find { it.sunset.isAfter(now) }?.let { sunsetData ->
        twentyFourHoursWeatherData.add(
            TwentyFourHoursWeatherData(
                time = sunsetData.sunset,
                temperature = 0.0,
                weatherType = WeatherType.fromWMO(0, false),
                sunset = sunsetData.sunset
            )
        )
    }

    return WeatherInfo(
        currentWeatherData = currentWeather.toCurrentWeatherData(),
        twentyFourHoursWeatherData = twentyFourHoursWeatherData.sortedBy { it.time },
        hourlyWeatherData = hourlyWeatherData,
        dailyWeatherData = dailyWeatherData,
        dailyWeatherSummaryData = hourlyWeatherData.toWeatherByTimePeriods(context)
    )
}
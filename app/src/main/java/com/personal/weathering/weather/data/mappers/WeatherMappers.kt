package com.personal.weathering.weather.data.mappers

import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapIndexed
import androidx.compose.ui.util.fastMapNotNull
import com.personal.weathering.R
import com.personal.weathering.core.util.convertStringToDate
import com.personal.weathering.core.util.convertStringToDateTime
import com.personal.weathering.weather.data.models.CurrentWeatherDto
import com.personal.weathering.weather.data.models.DailyWeatherDto
import com.personal.weathering.weather.data.models.HourlyWeatherDto
import com.personal.weathering.weather.data.models.WeatherDto
import com.personal.weathering.weather.domain.models.CurrentWeatherData
import com.personal.weathering.weather.domain.models.DailyWeatherData
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import com.personal.weathering.weather.domain.models.HourlyWeatherData
import com.personal.weathering.weather.domain.models.TwentyFourHoursWeatherData
import com.personal.weathering.weather.domain.models.WeatherInfo
import com.personal.weathering.weather.domain.models.WeatherSummaryData
import com.personal.weathering.weather.domain.models.WeatherType
import com.personal.weathering.weather.domain.models.WindDirectionType
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.math.roundToInt

private data class IndexedHourlyWeather(
    val index: Int,
    val data: HourlyWeatherData
)

private fun Int.toBoolean() = this == 1

fun CurrentWeatherDto.toCurrentWeatherData(): CurrentWeatherData {
    return CurrentWeatherData(
        time = convertStringToDateTime(time),
        temperature = temperature,
        apparentTemperature = apparentTemperature,
        humidity = humidity,
        weatherType = WeatherType.fromWMO(weatherCode, isDay?.toBoolean() ?: true),
        pressure = pressure,
        windSpeed = windSpeed,
        windDirection = windDirection?.toFloat(),
        windDirectionType = WindDirectionType.fromDegree(windDirection)
    )
}
fun HourlyWeatherDto.toHourlyWeatherData(): Map<Int, List<HourlyWeatherData>> {
    return time.fastMapIndexed { index, time ->
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
                time = convertStringToDateTime(time),
                temperature = temperature,
                apparentTemperature = apparentTemperature,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode, isDay?.toBoolean() ?: true),
                pressure = pressure,
                windSpeed = windSpeed,
                windDirection = windDirection?.toFloat(),
                windDirectionType = WindDirectionType.fromDegree(windDirection)
            )
        )
    }.groupBy { indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.fastMap { indexedData -> indexedData.data }
    }
}

fun DailyWeatherDto.toDailyWeatherData(): List<DailyWeatherData> {
    return time.fastMapIndexed { index, time ->
        val temperatureMax = temperaturesMax[index]
        val temperatureMin = temperaturesMin[index]
        val weatherCode = weatherCodes[index]
        val windSpeedMax = windSpeedsMax[index]
        val dominantWindDirection = dominantWindDirections[index]
        val daylightDuration = daylightDurations[index]
        val sunrise = sunrises[index]
        val sunset = sunsets[index]
        DailyWeatherData(
            time = convertStringToDate(time),
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            weatherType = WeatherType.fromWMO(weatherCode, true),
            windSpeedMax = windSpeedMax,
            dominantWindDirection = dominantWindDirection?.toFloat(),
            dominantWindDirectionType = WindDirectionType.fromDegree(dominantWindDirection),
            daylightDuration = Duration.ofMillis(((daylightDuration ?: 0.0) * 1000).toLong()),
            sunrise = convertStringToDateTime(sunrise),
            sunset = convertStringToDateTime(sunset)
        )
    }
}

private fun Map<Int, List<HourlyWeatherData>>.toWeatherByTimePeriods(): Map<Int, List<DailyWeatherSummaryData>> {
    val timeRanges = mapOf(
        R.string.morning to (4..11),
        R.string.day to (12..15),
        R.string.evening to (16..20),
        R.string.night to (21..23)
    )
    return this.mapValues { (_, hourlyData) ->
        timeRanges.map { (rangeRes, timeRange) ->
            val periodWeatherData = hourlyData.fastFilter { (it.time?.hour ?: 0) in timeRange }
            DailyWeatherSummaryData(
                periodRes = rangeRes,
                weatherSummary = periodWeatherData.toOverallWeather()
            )
        }
    }
}

private fun List<HourlyWeatherData>.toOverallWeather(): WeatherSummaryData {
    val windDirection = averageBy { it.windDirection?.toDouble() }?.toFloat()
    return WeatherSummaryData(
        temperature = averageBy { it.temperature },
        apparentTemperature = averageBy { it.apparentTemperature },
        humidity = averageBy { it.humidity?.toDouble() }?.roundToInt(),
        weatherType = mostCommonBy { it.weatherType },
        pressure = averageBy { it.pressure },
        windSpeed = averageBy { it.windSpeed },
        windDirection = windDirection,
        windDirectionType = WindDirectionType.fromDegree(windDirection?.roundToInt())
    )
}

private fun List<HourlyWeatherData>.averageBy(selector: (HourlyWeatherData) -> Double?): Double? {
    val nonNullValues = fastMapNotNull(selector)
    return if (nonNullValues.isNotEmpty()) nonNullValues.average() else null
}

private fun <T> List<HourlyWeatherData>.mostCommonBy(selector: (HourlyWeatherData) -> T): T? {
    return groupingBy(selector).eachCount().maxByOrNull { it.value }?.key
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(utcOffset)))
    val hourlyWeatherData = hourlyWeather.toHourlyWeatherData()
    val flattenHourlyWeatherData = hourlyWeatherData.values.flatten()

    val twentyFourHoursWeatherData = flattenHourlyWeatherData.asSequence().filter { hourlyData ->
        hourlyData.time?.isAfter(now) == true && hourlyData.time.isBefore(now.plusDays(1))
    }.sortedBy { it.time }.take(24).map { hourlyData ->
        TwentyFourHoursWeatherData(
            time = hourlyData.time,
            temperature = hourlyData.temperature,
            weatherType = hourlyData.weatherType
        )
    }.toMutableList()

    val dailyWeatherData = dailyWeather.toDailyWeatherData()
    dailyWeatherData.find { it.sunrise?.isAfter(now) == true }?.let { sunriseData ->
        twentyFourHoursWeatherData.add(
            TwentyFourHoursWeatherData(
                time = sunriseData.sunrise,
                temperature = 0.0,
                weatherType = WeatherType.fromWMO(0, false),
                sunrise = sunriseData.sunrise
            )
        )
    }
    dailyWeatherData.find { it.sunset?.isAfter(now) == true }?.let { sunsetData ->
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
        dailyWeatherSummaryData = hourlyWeatherData.toWeatherByTimePeriods()
    )
}
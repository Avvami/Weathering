package com.personal.weatherapp.data.mappers

import com.personal.weatherapp.data.remote.AQDataDto
import com.personal.weatherapp.data.remote.AQDto
import com.personal.weatherapp.domain.airquality.AQData
import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.airquality.AQType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedAQData(
    val index: Int,
    val data: AQData
)

fun AQDataDto.toAQDataMap(): Map<Int, List<AQData>> {
    return time.mapIndexed { index, time ->
        val particulateMatter10 = particulateMatters10[index]
        val particulateMatter25 = particulateMatters25[index]
        val carbonMonoxide = carbonMonoxides[index]
        val nitrogenDioxide = nitrogenDioxides[index]
        val sulphurDioxide = sulphurDioxides[index]
        val airQualityUS = airQualityUS[index]
        IndexedAQData(
            index = index,
            data = AQData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                particulateMatter10 = particulateMatter10,
                particulateMatter25 = particulateMatter25,
                carbonMonoxide = carbonMonoxide,
                nitrogenDioxide = nitrogenDioxide,
                sulphurDioxide = sulphurDioxide,
                aqType = AQType.fromAQI(airQualityUS)
            )
        )
    }.groupBy{ indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.map { indexedAQData -> indexedAQData.data }
    }
}

fun AQDto.toAQInfo(): AQInfo {
    val aqDataMap = aqData.toAQDataMap()
    val now = LocalDateTime.now()
    val currentAQData = if (now.hour == 23 && now.minute > 30) {
        val tomorrowData = aqDataMap[1]?.find { it.time.hour == 0 }
        val todayData = aqDataMap[0]?.find { it.time.hour == now.hour }
        tomorrowData?.let { tomorrow ->
            todayData?.let { today ->
                AQData(
                    time = today.time,
                    particulateMatter10 = tomorrow.particulateMatter10,
                    particulateMatter25 = tomorrow.particulateMatter25,
                    carbonMonoxide = tomorrow.carbonMonoxide,
                    nitrogenDioxide = tomorrow.nitrogenDioxide,
                    sulphurDioxide = tomorrow.sulphurDioxide,
                    aqType = tomorrow.aqType
                )
            }
        }
    } else {
        aqDataMap[0]?.find { today ->
            today.time.hour == when {
                now.minute < 30 -> now.hour
                else -> now.hour + 1
            }
        }
    }
    return AQInfo(
        currentAQData = currentAQData
    )
}
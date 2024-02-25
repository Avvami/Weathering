package com.personal.weathering.data.mappers

import com.personal.weathering.data.models.AqDto
import com.personal.weathering.data.models.CurrentAqDto
import com.personal.weathering.data.models.HourlyAqDto
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.models.airquality.CurrentAqData
import com.personal.weathering.domain.models.airquality.EuropeanAqType
import com.personal.weathering.domain.models.airquality.HourlyAqData
import com.personal.weathering.domain.models.airquality.UsAqType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedHourlyAq(
    val index: Int,
    val data: HourlyAqData
)

fun HourlyAqDto.toHourlyAqData(): Map<Int, List<HourlyAqData>> {
    return time.mapIndexed { index, time ->
        val europeanAqi = europeanAqies[index]
        val usAqi = usAqies[index]
        val particulateMatter10 = particulateMatters10[index]
        val particulateMatter25 = particulateMatters25[index]
        val carbonMonoxide = carbonMonoxides[index]
        val nitrogenDioxide = nitrogenDioxides[index]
        val sulphurDioxide = sulphurDioxides[index]
        val ozone = ozones[index]
        IndexedHourlyAq(
            index = index,
            data = HourlyAqData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                europeanAqi = europeanAqi,
                europeanAqiType = EuropeanAqType.fromAQI(europeanAqi),
                usAqi = usAqi,
                usAqiType = UsAqType.fromAQI(usAqi),
                particulateMatter10 = particulateMatter10,
                particulateMatter25 = particulateMatter25,
                carbonMonoxide = carbonMonoxide,
                nitrogenDioxide = nitrogenDioxide,
                sulphurDioxide = sulphurDioxide,
                ozone = ozone
            )
        )
    }.groupBy{ indexedData ->
        indexedData.index / 24
    }.mapValues {
        it.value.map { indexedData -> indexedData.data }
    }
}

fun CurrentAqDto.toCurrentAqData(): CurrentAqData {
    return CurrentAqData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
        europeanAqi = europeanAqi,
        europeanAqiType = EuropeanAqType.fromAQI(europeanAqi),
        usAqi = usAqi,
        usAqiType = UsAqType.fromAQI(usAqi),
        particulateMatter10 = particulateMatter10,
        particulateMatter25 = particulateMatter25,
        carbonMonoxide = carbonMonoxide,
        nitrogenDioxide = nitrogenDioxide,
        sulphurDioxide = sulphurDioxide,
        ozone = ozone
    )
}

fun AqDto.toAqInfo(): AqInfo {
    return AqInfo(
        currentAqData = currentAq.toCurrentAqData(),
        hourlyAqData = hourlyAq.toHourlyAqData()
    )
}
package com.personal.weathering.aq.data.mappers

import com.personal.weathering.aq.data.models.AqDto
import com.personal.weathering.aq.data.models.CurrentAqDto
import com.personal.weathering.aq.data.models.HourlyAqDto
import com.personal.weathering.aq.domain.models.AqInfo
import com.personal.weathering.aq.domain.models.CurrentAqData
import com.personal.weathering.aq.domain.models.EuAqType
import com.personal.weathering.aq.domain.models.HourlyAqData
import com.personal.weathering.aq.domain.models.UsAqType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedHourlyAq(
    val index: Int,
    val data: HourlyAqData
)

fun HourlyAqDto.toHourlyAqData(): Map<Int, List<HourlyAqData>> {
    return time.mapIndexed { index, time ->
        val euAqi = euAqies[index]
        val usAqi = usAqies[index]
        val particulateMatter10 = particulateMatters10[index]
        val particulateMatter25 = particulateMatters25[index]
        val carbonMonoxide = carbonMonoxides[index]
        val nitrogenDioxide = nitrogenDioxides[index]
        val sulphurDioxide = sulphurDioxides[index]
        val ozone = ozones[index]
        val usAqiParticulateMatter10 = usAqiParticulateMatters10[index]
        val usAqiParticulateMatter25 = usAqiParticulateMatters25[index]
        val usAqiCarbonMonoxide = usAqiCarbonMonoxides[index]
        val usAqiNitrogenDioxide = usAqiNitrogenDioxides[index]
        val usAqiSulphurDioxide = usAqiSulphurDioxides[index]
        val usAqiOzone = usAqiOzones[index]
        val euAqiParticulateMatter10 = euAqiParticulateMatters10[index]
        val euAqiParticulateMatter25 = euAqiParticulateMatters25[index]
        val euAqiNitrogenDioxide = euAqiNitrogenDioxides[index]
        val euAqiSulphurDioxide = euAqiSulphurDioxides[index]
        val euAqiOzone = euAqiOzones[index]
        IndexedHourlyAq(
            index = index,
            data = HourlyAqData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                euAqiType = EuAqType.fromAQI(euAqi),
                usAqiType = UsAqType.fromAQI(usAqi),
                particulateMatter10 = particulateMatter10,
                particulateMatter25 = particulateMatter25,
                carbonMonoxide = carbonMonoxide,
                nitrogenDioxide = nitrogenDioxide,
                sulphurDioxide = sulphurDioxide,
                ozone = ozone,
                usAqiParticulateMatter10Type = UsAqType.fromAQI(usAqiParticulateMatter10),
                usAqiParticulateMatter25Type = UsAqType.fromAQI(usAqiParticulateMatter25),
                usAqiCarbonMonoxideType = UsAqType.fromAQI(usAqiCarbonMonoxide),
                usAqiNitrogenDioxideType = UsAqType.fromAQI(usAqiNitrogenDioxide),
                usAqiSulphurDioxideType = UsAqType.fromAQI(usAqiSulphurDioxide),
                usAqiOzoneType = UsAqType.fromAQI(usAqiOzone),
                euAqiParticulateMatter10Type = EuAqType.fromAQI(euAqiParticulateMatter10),
                euAqiParticulateMatter25Type = EuAqType.fromAQI(euAqiParticulateMatter25),
                euAqiNitrogenDioxideType = EuAqType.fromAQI(euAqiNitrogenDioxide),
                euAqiSulphurDioxideType = EuAqType.fromAQI(euAqiSulphurDioxide),
                euAqiOzoneType = EuAqType.fromAQI(euAqiOzone)
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
        euAqiType = EuAqType.fromAQI(euAqi),
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
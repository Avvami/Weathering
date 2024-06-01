package com.personal.weathering.aq.domain.models

import java.time.LocalDateTime

data class HourlyAqData(
    val time: LocalDateTime,
    val euAqiType: EuAqType,
    val usAqiType: UsAqType,
    val particulateMatter10: Double,
    val particulateMatter25: Double,
    val carbonMonoxide: Double,
    val nitrogenDioxide: Double,
    val sulphurDioxide: Double,
    val ozone: Double,
    val usAqiParticulateMatter10Type: UsAqType,
    val usAqiParticulateMatter25Type: UsAqType,
    val usAqiCarbonMonoxideType: UsAqType,
    val usAqiNitrogenDioxideType: UsAqType,
    val usAqiSulphurDioxideType: UsAqType,
    val usAqiOzoneType: UsAqType,
    val euAqiParticulateMatter10Type: EuAqType,
    val euAqiParticulateMatter25Type: EuAqType,
    val euAqiNitrogenDioxideType: EuAqType,
    val euAqiSulphurDioxideType: EuAqType,
    val euAqiOzoneType: EuAqType
)

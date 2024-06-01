package com.personal.weathering.aq.domain.models

import java.time.LocalDateTime

data class CurrentAqData(
    val time: LocalDateTime,
    val euAqiType: EuAqType,
    val usAqiType: UsAqType,
    val particulateMatter10: Double,
    val particulateMatter25: Double,
    val carbonMonoxide: Double,
    val nitrogenDioxide: Double,
    val sulphurDioxide: Double,
    val ozone: Double
)

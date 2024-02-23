package com.personal.weathering.domain.airquality

import java.time.LocalDateTime

data class AQData(
    val time: LocalDateTime,
    val particulateMatter10: Double?,
    val particulateMatter25: Double?,
    val carbonMonoxide: Double?,
    val nitrogenDioxide: Double?,
    val sulphurDioxide: Double?,
    val aqType: AQType
)

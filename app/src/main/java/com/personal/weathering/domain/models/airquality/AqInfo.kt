package com.personal.weathering.domain.models.airquality

data class AqInfo(
    val currentAqData: CurrentAqData,
    val hourlyAqData: Map<Int, List<HourlyAqData>>
)

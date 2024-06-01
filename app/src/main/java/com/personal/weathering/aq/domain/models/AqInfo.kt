package com.personal.weathering.aq.domain.models

data class AqInfo(
    val currentAqData: CurrentAqData,
    val hourlyAqData: Map<Int, List<HourlyAqData>>
)

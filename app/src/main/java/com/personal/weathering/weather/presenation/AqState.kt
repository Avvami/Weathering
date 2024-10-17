package com.personal.weathering.weather.presenation

import com.personal.weathering.aq.domain.models.AqInfo

data class AqState(
    val aqInfo: AqInfo? = null,
    val isCollapsed: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

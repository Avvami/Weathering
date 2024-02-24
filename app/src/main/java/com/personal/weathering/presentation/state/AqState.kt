package com.personal.weathering.presentation.state

import com.personal.weathering.domain.models.airquality.AqInfo

data class AqState(
    val aqInfo: AqInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

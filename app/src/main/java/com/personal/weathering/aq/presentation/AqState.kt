package com.personal.weathering.aq.presentation

import com.personal.weathering.aq.domain.models.AqInfo

data class AqState(
    val aqInfo: AqInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

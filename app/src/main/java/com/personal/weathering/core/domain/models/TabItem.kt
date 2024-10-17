package com.personal.weathering.core.domain.models

import java.time.LocalDate

data class TabItem(
    val time: LocalDate,
    val dayOfMonth: Int,
    val dayOfWeek: String
)

package com.personal.weathering.core.util

import androidx.compose.runtime.Composable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun timeFormat(time: LocalDateTime, use12hour: Boolean): String {
    return if (!use12hour) {
        time.format(DateTimeFormatter.ofPattern("H:mm"))
    } else {
        time.format(DateTimeFormatter.ofPattern("h:mm a"))
    }
}

@Composable
fun hourFormat(time: LocalDateTime, use12hour: Boolean): String {
    return if (!use12hour) {
        time.format(DateTimeFormatter.ofPattern("HH"))
    } else {
        time.format(DateTimeFormatter.ofPattern("hh a"))
    }
}

fun formatDoubleValue(value: Double): String {
    val roundedValue = Math.round(value * 10) / 10.0
    return when {
        roundedValue % 1.0 == 0.0 -> roundedValue.toInt().toString()
        else -> "%.1f".format(value)
    }
}
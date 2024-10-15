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
package com.personal.weathering.domain.util

import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun timeFormat(time: LocalDateTime): String {
    return if (DateFormat.is24HourFormat(LocalContext.current)) {
        time.format(DateTimeFormatter.ofPattern("H:mm"))
    } else {
        time.format(DateTimeFormatter.ofPattern("h:mm a"))
    }
}
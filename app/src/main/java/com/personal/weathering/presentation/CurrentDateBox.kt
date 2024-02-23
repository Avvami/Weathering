package com.personal.weathering.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CurrentDateBox(
    time: LocalDateTime,
    modifier: Modifier,
    surfaceColor: Color,
    onSurfaceColor: Color
) {
    Box(modifier = modifier
        .clip(shape = MaterialTheme.shapes.extraLarge)
        .background(onSurfaceColor)
        .padding(12.dp, 4.dp)
    ) {
        Text(
            text = time.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM")),
            color = surfaceColor,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
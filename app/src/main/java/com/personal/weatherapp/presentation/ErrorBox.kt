package com.personal.weatherapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.weatherapp.R

@Composable
fun ErrorBox(
    error: String,
    modifier: Modifier,
    surfaceColor: Color,
    plainTextColor: Color,
    uiEvent: (UIEvent) -> Unit
) {
    Box(
        modifier = modifier
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_running_with_errors_fill1), contentDescription = "Error", tint = surfaceColor)
                Text(text = "Oops", style = MaterialTheme.typography.titleLarge, color = surfaceColor)
            }
            Text(text = "I think maybe it’s an error. You should check it out:", style = MaterialTheme.typography.titleMedium, color = plainTextColor)
            Text(text = error, style = MaterialTheme.typography.bodyMedium, color = plainTextColor)
            TextButton(onClick = { uiEvent(UIEvent.LoadWeatherInfo) }, modifier = Modifier.align(
                Alignment.End)) {
                Text(text = "Reload", color = surfaceColor)
            }
        }
    }
}
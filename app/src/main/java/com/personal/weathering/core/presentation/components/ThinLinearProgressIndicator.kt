package com.personal.weathering.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThinLinearProgressIndicator() {
    LinearProgressIndicator(
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth().height(1.dp)
    )
}
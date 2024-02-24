package com.personal.weathering.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p

@Composable
fun ThinLinearProgressIndicator() {
    LinearProgressIndicator(
        color = weatheringDarkBlue,
        trackColor = weatheringDarkBlue3p,
        modifier = Modifier.fillMaxWidth().height(2.dp)
    )
}
package com.personal.weathering.presentation.ui.screens.aq

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.weathering.presentation.UIEvent
import com.personal.weathering.presentation.WeatherState

@Composable
fun AQScreen(
    state: WeatherState,
    modifier: Modifier,
    uiEvent: (UIEvent) -> Unit
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            CurrentAQData(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                uiEvent = uiEvent
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
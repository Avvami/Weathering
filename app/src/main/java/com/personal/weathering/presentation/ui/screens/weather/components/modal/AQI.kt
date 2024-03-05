package com.personal.weathering.presentation.ui.screens.weather.components.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.PreferencesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AQI(
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.air_quality_index), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            SegmentedButton(
                selected = !preferencesState.value.useUSaq,
                onClick = { uiEvent(UiEvent.SetAqiUnit(false)) },
                shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                label = { Text(text = stringResource(id = R.string.european_aqi)) },
                colors = SegmentedButtonDefaults.colors(
                    activeContentColor = MaterialTheme.colorScheme.surface,
                    activeContainerColor = MaterialTheme.colorScheme.onSurface,
                    activeBorderColor = MaterialTheme.colorScheme.onSurface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
                    inactiveContainerColor = Color.Transparent,
                    inactiveBorderColor = MaterialTheme.colorScheme.onSurface
                ),
                icon = {}
            )
            SegmentedButton(
                selected = preferencesState.value.useUSaq,
                onClick = { uiEvent(UiEvent.SetAqiUnit(true)) },
                shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                label = { Text(text = stringResource(id = R.string.us_aqi)) },
                colors = SegmentedButtonDefaults.colors(
                    activeContentColor = MaterialTheme.colorScheme.surface,
                    activeContainerColor = MaterialTheme.colorScheme.onSurface,
                    activeBorderColor = MaterialTheme.colorScheme.onSurface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
                    inactiveContainerColor = Color.Transparent,
                    inactiveBorderColor = MaterialTheme.colorScheme.onSurface
                ),
                icon = {}
            )
        }
    }
}
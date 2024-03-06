package com.personal.weathering.presentation.ui.screens.weather.components.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.PreferencesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Units(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = stringResource(id = R.string.measurement_units), style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.temperature_units), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = preferencesState.value.useCelsius,
                    onClick = { uiEvent(UiEvent.SetTemperatureUnit(true)) },
                    shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                    label = { Text(text = stringResource(id = R.string.celsius_unit)) },
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
                    selected = !preferencesState.value.useCelsius,
                    onClick = { uiEvent(UiEvent.SetTemperatureUnit(false)) },
                    shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                    label = { Text(text = stringResource(id = R.string.fahrenheit_unit)) },
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.wind_speed_units), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = !preferencesState.value.useKmPerHour,
                    onClick = { uiEvent(UiEvent.SetSpeedUnit(false)) },
                    shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                    label = { Text(text = stringResource(id = R.string.m_per_second_unit)) },
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
                    selected = preferencesState.value.useKmPerHour,
                    onClick = { uiEvent(UiEvent.SetSpeedUnit(true)) },
                    shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                    label = { Text(text = stringResource(id = R.string.km_per_hour_unit)) },
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.pressure_units), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = !preferencesState.value.useHpa,
                    onClick = { uiEvent(UiEvent.SetPressureUnit(false)) },
                    shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                    label = { Text(text = stringResource(id = R.string.mmHg_unit)) },
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
                    selected = preferencesState.value.useHpa,
                    onClick = { uiEvent(UiEvent.SetPressureUnit(true)) },
                    shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                    label = { Text(text = stringResource(id = R.string.hPa_unit)) },
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
}
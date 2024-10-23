package com.personal.weathering.settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.core.presentation.PreferencesState

@Composable
fun Data(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.data),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    uiEvent(
                        UiEvent.ShowDialog(
                            content = {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetTemperatureUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = preferencesState.value.useCelsius,
                                            onClick = {
                                                uiEvent(UiEvent.SetTemperatureUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.celsius_unit))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetTemperatureUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = !preferencesState.value.useCelsius,
                                            onClick = {
                                                uiEvent(UiEvent.SetTemperatureUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.fahrenheit_unit))
                                    }
                                }
                            },
                            confirmTextRes = R.string.cancel
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_device_thermostat_fill0_wght400),
                    contentDescription = "Temperature",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.temperature_units),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = if (preferencesState.value.useCelsius) stringResource(id = R.string.celsius_unit) else
                    stringResource(id = R.string.fahrenheit_unit),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    uiEvent(
                        UiEvent.ShowDialog(
                            content = {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetSpeedUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = !preferencesState.value.useKmPerHour,
                                            onClick = {
                                                uiEvent(UiEvent.SetSpeedUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.m_per_second_unit))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetSpeedUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = preferencesState.value.useKmPerHour,
                                            onClick = {
                                                uiEvent(UiEvent.SetSpeedUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.km_per_hour_unit))
                                    }
                                }
                            },
                            confirmTextRes = R.string.cancel
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                    contentDescription = "Wind",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.wind_speed_units),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = if (preferencesState.value.useKmPerHour) stringResource(id = R.string.km_per_hour_unit) else
                    stringResource(id = R.string.m_per_second_unit),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    uiEvent(
                        UiEvent.ShowDialog(
                            content = {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetPressureUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = preferencesState.value.useHpa,
                                            onClick = {
                                                uiEvent(UiEvent.SetPressureUnit(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.hPa_unit))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetPressureUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = !preferencesState.value.useHpa,
                                            onClick = {
                                                uiEvent(UiEvent.SetPressureUnit(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.mmHg_unit))
                                    }
                                }
                            },
                            confirmTextRes = R.string.cancel
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                    contentDescription = "Pressure",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.pressure_units),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = if (preferencesState.value.useHpa) stringResource(id = R.string.hPa_unit) else
                    stringResource(id = R.string.mmHg_unit),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { uiEvent(
                    UiEvent.ShowDialog(
                        content = {
                            Column(
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            uiEvent(UiEvent.SetAqiUnit(true))
                                            uiEvent(UiEvent.CloseDialog)
                                        }
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = preferencesState.value.useUSaq,
                                        onClick = {
                                            uiEvent(UiEvent.SetAqiUnit(true))
                                            uiEvent(UiEvent.CloseDialog)
                                        }
                                    )
                                    Text(text = stringResource(id = R.string.us_aqi))
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            uiEvent(UiEvent.SetAqiUnit(false))
                                            uiEvent(UiEvent.CloseDialog)
                                        }
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = !preferencesState.value.useUSaq,
                                        onClick = {
                                            uiEvent(UiEvent.SetAqiUnit(false))
                                            uiEvent(UiEvent.CloseDialog)
                                        }
                                    )
                                    Text(text = stringResource(id = R.string.european_aqi))
                                }
                            }
                        },
                        confirmTextRes = R.string.cancel
                    )
                ) }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
                    contentDescription = "Air Quality",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.air_quality_index),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = if (preferencesState.value.useUSaq) stringResource(id = R.string.us_aqi) else
                    stringResource(id = R.string.european_aqi),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
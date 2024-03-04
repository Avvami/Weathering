package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.BuildConfig
import com.personal.weathering.R
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    currentCityState: State<CurrentCityState>,
    weatherState: () -> WeatherState,
    uiEvent: (UiEvent) -> Unit
) {
    ModalDrawerSheet(
        drawerContentColor = weatheringDarkBlue,
        drawerContainerColor = weatheringBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
                                ),
                                icon = {}
                            )
                            SegmentedButton(
                                selected = !preferencesState.value.useCelsius,
                                onClick = { uiEvent(UiEvent.SetTemperatureUnit(false)) },
                                shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                                label = { Text(text = stringResource(id = R.string.fahrenheit_unit)) },
                                colors = SegmentedButtonDefaults.colors(
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
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
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
                                ),
                                icon = {}
                            )
                            SegmentedButton(
                                selected = preferencesState.value.useKmPerHour,
                                onClick = { uiEvent(UiEvent.SetSpeedUnit(true)) },
                                shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                                label = { Text(text = stringResource(id = R.string.km_per_hour_unit)) },
                                colors = SegmentedButtonDefaults.colors(
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
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
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
                                ),
                                icon = {}
                            )
                            SegmentedButton(
                                selected = preferencesState.value.useHpa,
                                onClick = { uiEvent(UiEvent.SetPressureUnit(true)) },
                                shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                                label = { Text(text = stringResource(id = R.string.hPa_unit)) },
                                colors = SegmentedButtonDefaults.colors(
                                    activeContentColor = weatheringBlue,
                                    activeContainerColor = weatheringDarkBlue,
                                    activeBorderColor = weatheringDarkBlue,
                                    inactiveContentColor = weatheringDarkBlue,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveBorderColor = weatheringDarkBlue
                                ),
                                icon = {}
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.air_quality_index), style = MaterialTheme.typography.titleMedium)
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SegmentedButton(
                            selected = !preferencesState.value.useUSaq,
                            onClick = { uiEvent(UiEvent.SetAqiUnit(false)) },
                            shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                            label = { Text(text = stringResource(id = R.string.european_aqi)) },
                            colors = SegmentedButtonDefaults.colors(
                                activeContentColor = weatheringBlue,
                                activeContainerColor = weatheringDarkBlue,
                                activeBorderColor = weatheringDarkBlue,
                                inactiveContentColor = weatheringDarkBlue,
                                inactiveContainerColor = Color.Transparent,
                                inactiveBorderColor = weatheringDarkBlue
                            ),
                            icon = {}
                        )
                        SegmentedButton(
                            selected = preferencesState.value.useUSaq,
                            onClick = { uiEvent(UiEvent.SetAqiUnit(true)) },
                            shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                            label = { Text(text = stringResource(id = R.string.us_aqi)) },
                            colors = SegmentedButtonDefaults.colors(
                                activeContentColor = weatheringBlue,
                                activeContainerColor = weatheringDarkBlue,
                                activeBorderColor = weatheringDarkBlue,
                                inactiveContentColor = weatheringDarkBlue,
                                inactiveContainerColor = Color.Transparent,
                                inactiveBorderColor = weatheringDarkBlue
                            ),
                            icon = {}
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.my_cities), style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large)
                            .background(weatheringDarkBlue)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(weight = .8f, fill = false)
                        ) {
                            Text(
                                text = stringResource(id = R.string.current),
                                style = MaterialTheme.typography.bodySmall,
                                color = weatheringBlue
                            )
                            Text(
                                text = currentCityState.value.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = weatheringBlue
                            )
                        }
                        weatherState().weatherInfo?.let { weatherInfo ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = weatherInfo.currentWeatherData.weatherType.iconSmallRes),
                                    contentDescription = stringResource(id = weatherInfo.currentWeatherData.weatherType.weatherDescRes),
                                    tint = weatheringBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.temperature,
                                        if (preferencesState.value.useCelsius) weatherInfo.currentWeatherData.temperature.roundToInt() else
                                            UnitsConverter.toFahrenheit(weatherInfo.currentWeatherData.temperature).roundToInt()
                                    ),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = weatheringBlue
                                )
                            }
                        }
                    }
                    if (favoritesState.value.isNotEmpty()) {
                        Column {
                            favoritesState.value.forEach { favorite ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = weatheringDarkBlue,
                                            shape = MaterialTheme.shapes.large
                                        )
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = favorite.city,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Text(
                text = stringResource(
                    id = R.string.app_version,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = weatheringDarkBlue70p,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}
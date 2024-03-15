package com.personal.weathering.presentation.ui.screens.weather.components.modal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import kotlin.math.roundToInt

@Composable
fun CurrentLocation(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    weatherState: () -> WeatherState,
    setUseLocation: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.my_cities),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.onSurface)
                .clickable { setUseLocation() }
                .padding(start = 16.dp, top = 16.dp, end = 4.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier/*.weight(weight = .8f, fill = false)*/
            ) {
                Text(
                    text = stringResource(id = R.string.current_location),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surface
                )
                Text(
                    text = preferencesState.value.currentLocationCity,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                weatherState().weatherInfo?.let { weatherInfo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = weatherInfo.currentWeatherData.weatherType.iconSmallRes),
                            contentDescription = stringResource(id = weatherInfo.currentWeatherData.weatherType.weatherDescRes),
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) weatherInfo.currentWeatherData.temperature.roundToInt() else
                                    UnitsConverter.toFahrenheit(weatherInfo.currentWeatherData.temperature)
                                        .roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
                AnimatedContent(
                    targetState = preferencesState.value.useLocation,
                    label = "Location granted",
                    transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
                ) { targetState ->
                    if (targetState) {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_my_location_fill1_wght400),
                                contentDescription = "Location granted",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_location_disabled_fill1_wght400),
                                contentDescription = "Location not granted",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }
}
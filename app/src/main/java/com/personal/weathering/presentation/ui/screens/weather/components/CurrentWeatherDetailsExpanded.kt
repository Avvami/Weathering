package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.domain.util.shimmerEffect
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p

@Composable
fun CurrentWeatherDetailsExpanded(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo,
    aqState: () -> AqState,
    navigateToAqScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.large)
                .background(surfaceLight30p)
                .padding(horizontal = 4.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                    contentDescription = stringResource(id = R.string.wind),
                    tint = onSurfaceLight,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = if (preferencesState.value.useKmPerHour) R.string.km_per_hour else R.string.m_per_second,
                        if (preferencesState.value.useKmPerHour) weatherInfo().currentWeatherData.windSpeed else
                            UnitsConverter.toMetersPerSecond(weatherInfo().currentWeatherData.windSpeed)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = onSurfaceLight,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.wind),
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceLight
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                        contentDescription = weatherInfo().currentWeatherData.windDirectionType.direction,
                        tint = onSurfaceLight,
                        modifier = Modifier
                            .size(12.dp)
                            .rotate(degrees = (weatherInfo().currentWeatherData.windDirection + 180) % 360)
                    )
                    Text(
                        text = weatherInfo().currentWeatherData.windDirectionType.direction,
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceLight
                    )
                }
            }
            VerticalDivider(color = onSurfaceLight)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                    contentDescription = stringResource(id = R.string.pressure),
                    tint = onSurfaceLight,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = if (preferencesState.value.useHpa) R.string.hPa else R.string.mmHg,
                        if (preferencesState.value.useHpa) weatherInfo().currentWeatherData.pressure else
                            UnitsConverter.toMmHg(weatherInfo().currentWeatherData.pressure)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = onSurfaceLight,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.pressure),
                    style = MaterialTheme.typography.bodySmall,
                    color = onSurfaceLight
                )
            }
            VerticalDivider(color = onSurfaceLight)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = weatherInfo().currentWeatherData.humidityType.iconRes),
                    contentDescription = weatherInfo().currentWeatherData.humidityType.iconDesc,
                    tint = onSurfaceLight,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.percent, weatherInfo().currentWeatherData.humidity),
                    style = MaterialTheme.typography.titleMedium,
                    color = onSurfaceLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.humidity),
                    style = MaterialTheme.typography.bodySmall,
                    color = onSurfaceLight
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .clickable { navigateToAqScreen() }
                .background(surfaceLight30p)
                .padding(
                    start = 12.dp,
                    top = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
                contentDescription = stringResource(id = R.string.aqi),
                tint = onSurfaceLight,
                modifier = Modifier.size(36.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (aqState().aqInfo != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = if (preferencesState.value.useUSaq) painterResource(id = aqState().aqInfo!!.currentAqData.usAqiType.iconSmallRes) else
                                painterResource(id = aqState().aqInfo!!.currentAqData.euAqiType.iconSmallRes),
                            contentDescription = if (preferencesState.value.useUSaq) stringResource(id = aqState().aqInfo!!.currentAqData.usAqiType.aqIndexRes) else
                                stringResource(id = aqState().aqInfo!!.currentAqData.euAqiType.aqIndexRes),
                            tint = onSurfaceLight,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (preferencesState.value.useUSaq) stringResource(id = aqState().aqInfo!!.currentAqData.usAqiType.aqIndexRes) else
                                stringResource(id = aqState().aqInfo!!.currentAqData.euAqiType.aqIndexRes),
                            style = MaterialTheme.typography.titleSmall,
                            color = onSurfaceLight,
                            modifier = Modifier.weight(weight = .5f, fill = false)
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    ) {
                        Box(
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.good),
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Transparent
                        )
                    }
                }
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_arrow_right_alt_fill0_wght400),
                        contentDescription = "Arrow right",
                        tint = onSurfaceLight
                    )
                }
            }
        }
    }
}
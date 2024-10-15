package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.UnitsConverter
import com.personal.weathering.core.util.convertToFahrenheit
import com.personal.weathering.core.util.convertToMetersPerSecond
import com.personal.weathering.core.util.convertToMmHg
import com.personal.weathering.core.util.hourFormat
import com.personal.weathering.core.util.shimmerEffect
import com.personal.weathering.core.util.timeFormat
import com.personal.weathering.ui.theme.scrimLight
import com.personal.weathering.ui.theme.surfaceLight
import com.personal.weathering.weather.domain.models.WeatherInfo
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherDataPanel(
    preferencesState: State<PreferencesState>,
    weatherInfo: WeatherInfo,
    retrievingLocation: Boolean
) {
    Box(
        modifier = Modifier.clip(MaterialTheme.shapes.extraLarge)
    ) {
        weatherInfo.currentWeatherData.weatherType?.let { weatherType ->
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painterResource(id = weatherType.weatherImageRes),
                contentDescription = "Weather Image",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .background(scrimLight.copy(.25f))
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Rounded.Place,
                    contentDescription = "Location",
                    tint = surfaceLight
                )
                if (retrievingLocation) {
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect(),
                        text = "Great London",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Transparent,
                        maxLines = 1
                    )
                } else {
                    with(preferencesState.value) {
                        Text(
                            text = if (useLocation) currentLocationCity else selectedCity,
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = surfaceLight
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    weatherInfo.currentWeatherData.temperature?.let { temperature ->
                        Text(
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                    UnitsConverter.toFahrenheit(temperature).roundToInt()
                            ),
                            fontSize = 68.sp,
                            color = surfaceLight
                        )
                    }
                    weatherInfo.currentWeatherData.apparentTemperature?.let { apparentTemperature ->
                        Text(
                            text = stringResource(
                                id = R.string.apparent_temperature,
                                if (preferencesState.value.useCelsius) apparentTemperature.roundToInt() else
                                    UnitsConverter.toFahrenheit(apparentTemperature).roundToInt()
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = surfaceLight.copy(alpha = .8f)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    weatherInfo.currentWeatherData.weatherType?.let { weatherType ->
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = weatherType.iconFilledRes),
                            contentDescription = stringResource(id = weatherType.weatherDescRes),
                            tint = surfaceLight
                        )
                        Text(
                            text = stringResource(id = weatherType.weatherDescRes),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.End,
                            color = surfaceLight
                        )
                    }
                }
            }
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    with(weatherInfo.currentWeatherData) {
                        buildList<@Composable ColumnScope.() -> Unit> {
                            windSpeed?.let { windSpeed ->
                                add {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(22.dp),
                                            painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                                            contentDescription = "Wind",
                                            tint = surfaceLight.copy(.7f)
                                        )
                                        val annotatedString = buildAnnotatedString {
                                            append(
                                                stringResource(
                                                    id = if (preferencesState.value.useKmPerHour) R.string.km_per_hour else R.string.m_per_second,
                                                    if (preferencesState.value.useKmPerHour) windSpeed else convertToMetersPerSecond(windSpeed)
                                                )
                                            )
                                            windDirectionType?.let {
                                                append(", ${stringResource(id = it.directionRes)}")
                                            }
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = annotatedString,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = surfaceLight,
                                                textAlign = TextAlign.Center
                                            )
                                            windDirection?.let { windDirection ->
                                                Icon(
                                                    modifier = Modifier
                                                        .size(12.dp)
                                                        .rotate(degrees = (windDirection + 180) % 360),
                                                    painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                                    contentDescription = null,
                                                    tint = surfaceLight
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            pressure?.let { pressure ->
                                add {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(22.dp),
                                            painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                                            contentDescription = "Pressure",
                                            tint = surfaceLight.copy(.7f)
                                        )
                                        Text(
                                            text = stringResource(
                                                id = if (preferencesState.value.useHpa) R.string.hPa else R.string.mmHg,
                                                if (preferencesState.value.useHpa) pressure else convertToMmHg(pressure)
                                            ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = surfaceLight
                                        )
                                    }
                                }
                            }
                            humidity?.let { humidity ->
                                add {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(22.dp),
                                            painter = painterResource(id = R.drawable.icon_water_drop_fill0_wght400),
                                            contentDescription = "Humidity",
                                            tint = surfaceLight.copy(.7f)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.percent, humidity),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = surfaceLight
                                        )
                                    }
                                }
                            }
                        }.takeIf { it.isNotEmpty() }?.let { components ->
                            item {
                                Column(
                                    modifier = Modifier
                                        .heightIn(min = 128.dp)
                                        .clip(MaterialTheme.shapes.extraLarge)
                                        .background(scrimLight.copy(.3f))
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    components.fastForEach { component ->
                                        component()
                                    }
                                }
                            }
                        }
                    }
                    items(
                        items = weatherInfo.twentyFourHoursWeatherData,
                        key = { it.uniqueKey }
                    ) { weatherData ->
                        Column(
                            modifier = Modifier
                                .heightIn(min = 128.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .background(scrimLight.copy(.3f))
                                .padding(horizontal = 12.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            with(weatherData) {
                                time?.let { time ->
                                    Text(
                                        text = when {
                                            sunrise != null -> timeFormat(time = sunrise, use12hour = preferencesState.value.use12hour)
                                            sunset != null -> timeFormat(time = sunset, use12hour = preferencesState.value.use12hour)
                                            else -> hourFormat(time = time, use12hour = preferencesState.value.use12hour)
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = surfaceLight
                                    )
                                }
                                weatherType?.let { weatherType ->
                                    Icon(
                                        painter = when {
                                            sunrise != null -> painterResource(id = R.drawable.icon_wb_sunny_fill1_wght400)
                                            sunset != null -> painterResource(id = R.drawable.icon_wb_twilight_fill1_wght400)
                                            else -> painterResource(id = weatherType.iconFilledRes)
                                        },
                                        contentDescription = null,
                                        tint = surfaceLight
                                    )
                                }
                                temperature?.let { temperature ->
                                    Text(
                                        text = when {
                                            sunrise != null -> stringResource(id = R.string.sunrise)
                                            sunset != null -> stringResource(id = R.string.sunset)
                                            else -> stringResource(
                                                id = R.string.temperature,
                                                if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                                    convertToFahrenheit(temperature).roundToInt()
                                            )
                                        },
                                        style = MaterialTheme.typography.titleLarge,
                                        color = surfaceLight
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
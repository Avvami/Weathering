package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.UnitsConverter
import com.personal.weathering.core.util.timeFormat
import com.personal.weathering.ui.theme.onSurfaceLight
import com.personal.weathering.ui.theme.onSurfaceLight70p
import com.personal.weathering.weather.domain.models.WeatherInfo
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentWeatherTemperatureInfoExpanded(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    weatherInfo().currentWeatherData.temperature?.let { temperature ->
                        Text(
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                    UnitsConverter.toFahrenheit(temperature).roundToInt()
                            ),
                            fontSize = 68.sp,
                            color = onSurfaceLight
                        )
                    }
                    Icon(
                        painter = painterResource(id = weatherInfo().currentWeatherData.weatherType.iconSmallRes),
                        contentDescription = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
                        tint = onSurfaceLight,
                        modifier = Modifier.size(64.dp),
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                weatherInfo().currentWeatherData.apparentTemperature?.let { apparentTemperature ->
                    Text(
                        text = stringResource(
                            id = R.string.apparent_temperature,
                            if (preferencesState.value.useCelsius) apparentTemperature.roundToInt() else
                                UnitsConverter.toFahrenheit(apparentTemperature).roundToInt()
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = onSurfaceLight70p
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                weatherInfo().currentWeatherData.time?.let { time ->
                    Text(
                        text = stringResource(id = R.string.today_time, timeFormat(time = time, use12hour = preferencesState.value.use12hour)),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,
                        color = onSurfaceLight
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = onSurfaceLight
                )
            }
        }
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = weatherInfo().twentyFourHoursWeatherData,
                    key = { it.uniqueKey }
                ) { weatherData ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        weatherData.time?.let { time ->
                            Text(
                                text = when {
                                    weatherData.sunrise != null -> timeFormat(time = weatherData.sunrise, use12hour = preferencesState.value.use12hour)
                                    weatherData.sunset != null -> timeFormat(time = weatherData.sunset, use12hour = preferencesState.value.use12hour)
                                    else -> timeFormat(time = time, use12hour = preferencesState.value.use12hour)
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = onSurfaceLight70p
                            )
                            Text(
                                text = weatherData.time.format(DateTimeFormatter.ofPattern("dd MMM")),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (weatherData.time.hour == 0) onSurfaceLight70p else Color.Transparent
                            )
                        }
                        Icon(
                            painter = when {
                                weatherData.sunrise != null -> painterResource(id = R.drawable.icon_wb_sunny_fill1_wght400)
                                weatherData.sunset != null -> painterResource(id = R.drawable.icon_wb_twilight_fill1_wght400)
                                else -> painterResource(id = weatherData.weatherType.iconSmallRes)
                            },
                            contentDescription = when {
                                weatherData.sunrise != null -> stringResource(id = R.string.sunrise)
                                weatherData.sunset != null -> stringResource(id = R.string.sunset)
                                else -> stringResource(id = weatherData.weatherType.weatherDescRes)
                            },
                            tint = onSurfaceLight
                        )
                        weatherData.temperature?.let { temperature ->
                            Text(
                                text = "dd MMM",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Transparent
                            )
                            Text(
                                text = when {
                                    weatherData.sunrise != null -> stringResource(id = R.string.sunrise)
                                    weatherData.sunset != null -> stringResource(id = R.string.sunset)
                                    else -> stringResource(
                                        id = R.string.temperature,
                                        if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                            UnitsConverter.toFahrenheit(temperature).roundToInt()
                                    )
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = onSurfaceLight
                            )
                        }
                    }
                }
            }
        }
    }
}
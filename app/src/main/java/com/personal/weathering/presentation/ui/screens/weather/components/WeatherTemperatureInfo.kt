package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherTemperatureInfo(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(
                        id = R.string.temperature,
                        if (preferencesState.value.useCelsius) weatherInfo().currentWeatherData.temperature.roundToInt() else
                            UnitsConverter.toFahrenheit(weatherInfo().currentWeatherData.temperature).roundToInt()
                    ),
                    fontSize = 82.sp,
                    color = onSurfaceLight
                )
                Text(
                    text = stringResource(
                        id = R.string.apparent_temperature,
                        if (preferencesState.value.useCelsius) weatherInfo().currentWeatherData.apparentTemperature.roundToInt() else
                                UnitsConverter.toFahrenheit(weatherInfo().currentWeatherData.apparentTemperature).roundToInt()
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = onSurfaceLight70p
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.today_time, timeFormat(time = weatherInfo().currentWeatherData.time)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = onSurfaceLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = onSurfaceLight
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = weatherInfo().currentWeatherData.weatherType.iconLargeRes),
            contentDescription = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
            tint = onSurfaceLight,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
        )
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                weatherInfo().twentyFourHoursWeatherData.forEachIndexed { index, weatherData ->
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = when {
                                weatherData.sunrise != null -> timeFormat(time = weatherData.sunrise)
                                weatherData.sunset != null -> timeFormat(time = weatherData.sunset)
                                else -> timeFormat(time = weatherData.time)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = onSurfaceLight70p
                        )
                        if (weatherData.time.hour == 0) {
                            Text(
                                text = weatherData.time.format(DateTimeFormatter.ofPattern("dd MMM")),
                                style = MaterialTheme.typography.bodyMedium,
                                color = onSurfaceLight70p
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
                        Text(
                            text = when {
                                weatherData.sunrise != null -> stringResource(id = R.string.sunrise)
                                weatherData.sunset != null -> stringResource(id = R.string.sunset)
                                else -> stringResource(
                                    id = R.string.temperature,
                                    if (preferencesState.value.useCelsius) weatherData.temperature.roundToInt() else
                                        UnitsConverter.toFahrenheit(weatherData.temperature).roundToInt()
                                )
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = onSurfaceLight
                        )
                    }
                    if (index != weatherInfo().twentyFourHoursWeatherData.lastIndex) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = "Divider",
                            tint = onSurfaceLight,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(8.dp)
                        )
                    }
                }
            }
        }
    }
}
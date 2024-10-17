package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.domain.models.TabItem
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.convertToFahrenheit
import com.personal.weathering.core.util.convertToMetersPerSecond
import com.personal.weathering.core.util.convertToMmHg
import com.personal.weathering.core.util.timeFormat
import com.personal.weathering.weather.presenation.WeatherState
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun ListView(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    lazyListState: LazyListState,
    weatherState: WeatherState,
    tabItems: List<TabItem>
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(
            items = tabItems,
            key = { _, item -> item.dayOfMonth }
        ) { index, item ->
            weatherState.weatherInfo?.dailyWeatherSummaryData?.get(index)?.let { dailyWeatherSummaryData ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(.5f),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                val annotatedString = buildAnnotatedString {
                                    append(item.time.format(DateTimeFormatter.ofPattern("MMMM")) + ",\n")
                                    append(
                                        when (index) {
                                            0 -> stringResource(id = R.string.today)
                                            1 -> stringResource(id = R.string.tomorrow)
                                            else -> item.time.format(DateTimeFormatter.ofPattern("EEEE")).replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                            }
                                        }
                                    )
                                }
                                Text(
                                    text = annotatedString,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!dailyWeatherSummaryData.all { it.weatherSummary.windSpeed == null }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.wind) + if (preferencesState.value.useKmPerHour)
                                            ", ${stringResource(id = R.string.km_per_hour_unit)}"
                                        else
                                            ", ${stringResource(id = R.string.m_per_second_unit)}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                    )
                                }
                                if (!dailyWeatherSummaryData.all { it.weatherSummary.pressure == null }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.pressure) + if (preferencesState.value.useHpa)
                                            ",\n${stringResource(id = R.string.hPa_unit)}"
                                        else
                                            ",\n${stringResource(id = R.string.mmHg_unit)}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                    )
                                }
                                if (!dailyWeatherSummaryData.all { it.weatherSummary.humidity == null }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.humidity),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                    )
                                }
                                if (!dailyWeatherSummaryData.all { it.weatherSummary.apparentTemperature == null }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = stringResource(id = R.string.feels_like),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            dailyWeatherSummaryData.fastForEach { data ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(.5f),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(.5f),
                                            verticalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text(
                                                text = stringResource(id = data.periodRes),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                            )
                                            data.weatherSummary.temperature?.let { temperature ->
                                                Text(
                                                    text = stringResource(
                                                        id = R.string.temperature,
                                                        if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                                            convertToFahrenheit(temperature).roundToInt()
                                                    ),
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                        }
                                        data.weatherSummary.weatherType?.let { weatherType ->
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = weatherType.iconOutlinedRes),
                                                    contentDescription = stringResource(id = weatherType.weatherDescRes)
                                                )
                                                Text(
                                                    text = stringResource(id = weatherType.weatherDescRes),
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        with(data.weatherSummary) {
                                            windSpeed?.let { windSpeed ->
                                                Row(
                                                    modifier = Modifier.weight(1f),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text(
                                                        text = String.format(
                                                            Locale.getDefault(),
                                                            "%.1f",
                                                            if (preferencesState.value.useKmPerHour) windSpeed else convertToMetersPerSecond(windSpeed)
                                                        ),
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                                    ) {
                                                        windDirection?.let { windDirection ->
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(12.dp)
                                                                    .rotate(degrees = (windDirection + 180) % 360),
                                                                painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                                                contentDescription = null,
                                                                tint = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                                            )
                                                        }
                                                        windDirectionType?.let { windDirectionType ->
                                                            Text(
                                                                text = stringResource(id = windDirectionType.directionRes),
                                                                style = MaterialTheme.typography.bodySmall,
                                                                color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            pressure?.let { pressure ->
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = if (preferencesState.value.useHpa) "%.1f".format(pressure) else "%.1f".format(convertToMmHg(pressure)),
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                            humidity?.let { humidity ->
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = stringResource(id = R.string.percent, humidity),
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                            apparentTemperature?.let { apparentTemperature ->
                                                Text(
                                                    modifier = Modifier.weight(1f),
                                                    text = stringResource(
                                                        id = R.string.temperature,
                                                        if (preferencesState.value.useCelsius) apparentTemperature.roundToInt() else
                                                            convertToFahrenheit(apparentTemperature).roundToInt()
                                                    ),
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    with(weatherState.weatherInfo.dailyWeatherData[index]) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                        ) {
                            daylightDuration?.let { daylightDuration ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.daylight_duration),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                    )
                                    Text(
                                        text = stringResource(
                                            id = R.string.hours_minutes,
                                            daylightDuration.toHours(),
                                            daylightDuration.toMinutesPart()
                                        ),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(48.dp)
                            ) {
                                sunrise?.let { sunrise ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(id = R.drawable.icon_wb_sunny_fill0_wght400),
                                            contentDescription = stringResource(id = R.string.sunrise)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.sunrise),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                        )
                                        Text(
                                            text = timeFormat(time = sunrise, use12hour = preferencesState.value.use12hour),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                                sunset?.let { sunset ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(id = R.drawable.icon_wb_twilight_fill0_wght400),
                                            contentDescription = stringResource(id = R.string.sunset)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.sunset),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                        )
                                        Text(
                                            text = timeFormat(time = sunset, use12hour = preferencesState.value.use12hour),
                                            style = MaterialTheme.typography.titleMedium
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
}
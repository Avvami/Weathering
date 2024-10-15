package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.convertToFahrenheit
import com.personal.weathering.weather.domain.models.DailyWeatherData
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeeklyForecastPanel(
    navigateToForecastScreen: (dayOfWeek: Int) -> Unit,
    preferencesState: State<PreferencesState>,
    forecast: List<DailyWeatherData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.weekly_forecast),
                style = MaterialTheme.typography.titleLarge
            )
            FilledIconButton(
                modifier = Modifier.size(48.dp),
                onClick = { navigateToForecastScreen(0) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_outward_fill0_wght400),
                    contentDescription = "Details"
                )
            }
        }
        forecast.fastForEachIndexed { index, weatherData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .clickable {
                        navigateToForecastScreen(index)
                    }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                weatherData.time?.let { time ->
                    Column(
                        modifier = Modifier.weight(1f, false)
                    ) {
                        Text(
                            text = time.format(DateTimeFormatter.ofPattern("d MMMM")),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                        )
                        Text(
                            text = when (index) {
                                0 -> stringResource(id = R.string.today)
                                1 -> stringResource(id = R.string.tomorrow)
                                else -> time
                                    .format(DateTimeFormatter.ofPattern("EEEE"))
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    weatherData.weatherType?.let { weatherType ->
                        Icon(
                            modifier = Modifier
                                .widthIn(30.dp)
                                .size(22.dp),
                            painter = painterResource(id = weatherType.iconOutlinedRes),
                            contentDescription = stringResource(id = weatherType.weatherDescRes)
                        )
                    }
                    weatherData.temperatureMax?.let { temperatureMax ->
                        Text(
                            modifier = Modifier.widthIn(min = 30.dp),
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) temperatureMax.roundToInt() else convertToFahrenheit(temperatureMax).roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    weatherData.temperatureMin?.let { temperatureMin ->
                        Text(
                            modifier = Modifier.widthIn(min = 30.dp),
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) temperatureMin.roundToInt() else convertToFahrenheit(temperatureMin).roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
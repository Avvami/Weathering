package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.convertToFahrenheit
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import kotlin.math.roundToInt

@Composable
fun TemperaturePanel(
    preferencesState: State<PreferencesState>,
    summaryData: List<DailyWeatherSummaryData>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            summaryData.fastForEach { data ->
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = data.periodRes),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                summaryData.fastForEach { data ->
                    data.weatherSummary.temperature?.let { temperature ->
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            data.weatherSummary.weatherType?.let { weatherType ->
                                Icon(
                                    painter = painterResource(id = weatherType.iconOutlinedRes),
                                    contentDescription = stringResource(id = weatherType.weatherDescRes)
                                )
                            }
                            Text(
                                text = stringResource(
                                    id = R.string.temperature,
                                    if (preferencesState.value.useCelsius) temperature.roundToInt() else convertToFahrenheit(temperature).roundToInt()
                                ),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
            if (summaryData.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.feels_like),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                summaryData.fastForEach { data ->
                    data.weatherSummary.apparentTemperature?.let { apparentTemperature ->
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) apparentTemperature.roundToInt() else convertToFahrenheit(apparentTemperature).roundToInt()
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                        )
                    }
                }
            }
        }
    }
}
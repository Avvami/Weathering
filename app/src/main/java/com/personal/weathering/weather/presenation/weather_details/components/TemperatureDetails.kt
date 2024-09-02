package com.personal.weathering.weather.presenation.weather_details.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.UnitsConverter
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import com.personal.weathering.ui.theme.onSurfaceLight70p
import com.personal.weathering.ui.theme.surfaceLight30p
import kotlin.math.roundToInt

@Composable
fun TemperatureDetails(
    preferencesState: State<PreferencesState>,
    summaryData: List<DailyWeatherSummaryData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceLight30p)
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
                        Text(
                            text = stringResource(id = data.periodRes),
                            style = MaterialTheme.typography.labelLarge
                        )
                        data.weatherSummary.weatherType?.let { weatherType ->
                            Icon(
                                painter = painterResource(id = weatherType.iconSmallRes),
                                contentDescription = stringResource(id = weatherType.weatherDescRes)
                            )
                        }
                        Text(
                            text = stringResource(
                                id = R.string.temperature,
                                if (preferencesState.value.useCelsius) temperature.roundToInt() else
                                    UnitsConverter.toFahrenheit(temperature).roundToInt()

                            ),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.feels_like),
                style = MaterialTheme.typography.labelMedium,
                color = onSurfaceLight70p
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = onSurfaceLight70p
            )
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
                            if (preferencesState.value.useCelsius) apparentTemperature.roundToInt() else
                                UnitsConverter.toFahrenheit(apparentTemperature).roundToInt()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = onSurfaceLight70p,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
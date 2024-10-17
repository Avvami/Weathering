package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.convertToMetersPerSecond
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import java.util.Locale

@Composable
fun WindPanel(
    preferencesState: State<PreferencesState>,
    summaryData: List<DailyWeatherSummaryData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val annotatedString = buildAnnotatedString {
                append(stringResource(id = R.string.wind))
                if (preferencesState.value.useKmPerHour)
                    append(", ${stringResource(id = R.string.km_per_hour_unit)}")
                else
                    append(", ${stringResource(id = R.string.m_per_second_unit)}")
            }
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            summaryData.fastForEach { data ->
                data.weatherSummary.windSpeed?.let { windSpeed ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = String.format(
                                Locale.getDefault(),
                                "%.1f",
                                if (preferencesState.value.useKmPerHour) windSpeed else convertToMetersPerSecond(windSpeed)
                            ),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            data.weatherSummary.windDirection?.let { windDirection ->
                                Icon(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .rotate(degrees = (windDirection + 180) % 360),
                                    painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                )
                            }
                            data.weatherSummary.windDirectionType?.let { windDirectionType ->
                                Text(
                                    text = stringResource(id = windDirectionType.directionRes),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

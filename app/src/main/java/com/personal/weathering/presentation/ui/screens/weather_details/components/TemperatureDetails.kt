package com.personal.weathering.presentation.ui.screens.weather_details.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.DailyWeatherSummaryData
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import com.personal.weathering.presentation.ui.theme.surfaceLight30p
import kotlin.math.roundToInt

@Composable
fun TemperatureDetails(
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
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = data.period,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        painter = painterResource(id = data.weatherSummary.weatherType.iconSmallRes),
                        contentDescription = stringResource(id = data.weatherSummary.weatherType.weatherDescRes)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.temperature,
                            data.weatherSummary.temperature.roundToInt()
                        ),
                        style = MaterialTheme.typography.labelLarge
                    )
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
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(
                        id = R.string.temperature,
                        data.weatherSummary.apparentTemperature.roundToInt()
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = onSurfaceLight70p,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
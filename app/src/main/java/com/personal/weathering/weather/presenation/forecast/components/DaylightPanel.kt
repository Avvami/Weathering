package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.timeFormat
import com.personal.weathering.weather.domain.models.DailyWeatherData

@Composable
fun DaylightPanel(
    preferencesState: State<PreferencesState>,
    dailyWeatherData: DailyWeatherData
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
        ) {
            dailyWeatherData.sunrise?.let { sunrise ->
                Column(
                    modifier = Modifier.padding(top = 8.dp),
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
            dailyWeatherData.daylightDuration?.let { daylightDuration ->
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
            dailyWeatherData.sunset?.let { sunset ->
                Column(
                    modifier = Modifier.padding(top = 8.dp),
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
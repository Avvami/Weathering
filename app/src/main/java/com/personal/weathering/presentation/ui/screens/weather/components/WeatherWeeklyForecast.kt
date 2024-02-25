package com.personal.weathering.presentation.ui.screens.weather.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import java.time.format.DateTimeFormatter

@Composable
fun WeatherWeeklyForecast(
    weatherInfo: () -> WeatherInfo
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.weekly_forecast),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            weatherInfo().dailyWeatherData.forEachIndexed { index, weatherData ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .background(weatheringDarkBlue3p)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = weatherData.time.format(DateTimeFormatter.ofPattern("d MMMM")),
                            style = MaterialTheme.typography.labelLarge,
                            color = weatheringDarkBlue70p
                        )
                        Text(
                            text = when (index) {
                                0 -> stringResource(id = R.string.today)
                                1 -> stringResource(id = R.string.tomorrow)
                                else -> weatherData.time.format(DateTimeFormatter.ofPattern("EEEE"))
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = weatheringDarkBlue
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                            contentDescription = weatherData.weatherType.weatherDesc,
                            tint = weatheringDarkBlue,
                            modifier = Modifier.size(28.dp)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.max),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = weatheringDarkBlue70p
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.temperature, weatherData.temperatureMax),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = weatheringDarkBlue
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.min),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = weatheringDarkBlue70p
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.temperature, weatherData.temperatureMin),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = weatheringDarkBlue70p
                            )
                        }
                    }
                }
            }
        }
    }
}
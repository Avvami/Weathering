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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherTemperatureInfo(
    weatherInfo: () -> WeatherInfo
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.temperature, weatherInfo().currentWeatherData.temperature),
                    fontSize = 82.sp
                )
                Text(
                    text = stringResource(id = R.string.apparent_temperature, weatherInfo().currentWeatherData.apparentTemperature),
                    style = MaterialTheme.typography.titleMedium,
                    color = weatheringDarkBlue70p
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.today_time, timeFormat(time = weatherInfo().currentWeatherData.time)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = weatherInfo().currentWeatherData.weatherType.iconLargeRes),
            contentDescription = stringResource(id = weatherInfo().currentWeatherData.weatherType.weatherDescRes),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                weatherInfo().twentyFourHoursWeatherData.forEachIndexed { index, weatherData ->
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = timeFormat(time = weatherData.time),
                            style = MaterialTheme.typography.bodyMedium,
                            color = weatheringDarkBlue70p
                        )
                        if (weatherData.time.hour == 0) {
                            Text(
                                text = weatherData.time.format(DateTimeFormatter.ofPattern("dd MMM")),
                                style = MaterialTheme.typography.bodyMedium,
                                color = weatheringDarkBlue70p
                            )
                        }
                        Icon(
                            painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                            contentDescription = stringResource(id = weatherData.weatherType.weatherDescRes)
                        )
                        Text(
                            text = stringResource(id = R.string.temperature, weatherData.temperature),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    if (index != weatherInfo().twentyFourHoursWeatherData.lastIndex) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = "Divider",
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
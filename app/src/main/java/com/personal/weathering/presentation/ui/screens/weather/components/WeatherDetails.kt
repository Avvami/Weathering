package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue

@Composable
fun WeatherDetails(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo,
    aqState: () -> AqState,
    navigateToAqScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(horizontal = 24.dp)
            .clip(MaterialTheme.shapes.large)
            .background(weatheringDarkBlue)
            .padding(horizontal = 4.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                contentDescription = "Air",
                tint = weatheringBlue,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.km_per_hour, weatherInfo().currentWeatherData.windSpeed),
                style = MaterialTheme.typography.titleMedium,
                color = weatheringBlue,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.wind),
                    style = MaterialTheme.typography.bodySmall,
                    color = weatheringBlue
                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                    contentDescription = "Direction",
                    tint = weatheringBlue,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(degrees = (weatherInfo().currentWeatherData.windDirection + 180) % 360)
                )
                Text(
                    text = weatherInfo().currentWeatherData.windDirectionType.direction,
                    style = MaterialTheme.typography.bodySmall,
                    color = weatheringBlue
                )
            }
        }
        VerticalDivider(color = weatheringBlue)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                contentDescription = "Pressure",
                tint = weatheringBlue,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.hPa, weatherInfo().currentWeatherData.pressure),
                style = MaterialTheme.typography.titleMedium,
                color = weatheringBlue,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.pressure),
                style = MaterialTheme.typography.bodySmall,
                color = weatheringBlue
            )
        }
        VerticalDivider(color = weatheringBlue)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = weatherInfo().currentWeatherData.humidityType.iconRes),
                contentDescription = weatherInfo().currentWeatherData.humidityType.iconDesc,
                tint = weatheringBlue,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.percent, weatherInfo().currentWeatherData.humidity),
                style = MaterialTheme.typography.titleMedium,
                color = weatheringBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.humidity),
                style = MaterialTheme.typography.bodySmall,
                color = weatheringBlue
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable { navigateToAqScreen() }
            .background(weatheringDarkBlue)
            .padding(
                start = 12.dp,
                top = 4.dp,
                end = 4.dp,
                bottom = 4.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
            contentDescription = "AQ",
            tint = weatheringBlue,
            modifier = Modifier.size(36.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            aqState().aqInfo?.let { aqInfo ->
                Icon(
                    painter = if (preferencesState.value.useUSaq) painterResource(id = aqInfo.currentAqData.usAqiType.iconSmallRes) else
                        painterResource(id = aqInfo.currentAqData.europeanAqiType.iconSmallRes),
                    contentDescription = if (preferencesState.value.useUSaq) stringResource(id = aqInfo.currentAqData.usAqiType.aqDescRes) else
                        stringResource(id = aqInfo.currentAqData.europeanAqiType.aqDescRes),
                    tint = weatheringBlue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (preferencesState.value.useUSaq) stringResource(id = aqInfo.currentAqData.usAqiType.aqDescRes) else
                        stringResource(id = aqInfo.currentAqData.europeanAqiType.aqDescRes),
                    style = MaterialTheme.typography.titleSmall,
                    color = weatheringBlue,
                    modifier = Modifier.weight(weight = .5f, fill = false)
                )
            }
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_right_alt_fill0_wght400),
                    contentDescription = "Arrow right",
                    tint = weatheringBlue
                )
            }
        }
    }
}
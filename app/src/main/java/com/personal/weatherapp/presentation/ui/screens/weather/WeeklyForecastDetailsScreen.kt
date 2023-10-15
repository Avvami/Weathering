package com.personal.weatherapp.presentation.ui.screens.weather

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weatherapp.R
import com.personal.weatherapp.domain.util.timeFormat
import com.personal.weatherapp.domain.weather.WeatherData
import com.personal.weatherapp.presentation.ErrorBox
import com.personal.weatherapp.presentation.UIEvent
import com.personal.weatherapp.presentation.WeatherState
import com.personal.weatherapp.presentation.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun WeeklyForecastDetailsScreen(
    navigator: DestinationsNavigator,
    state: WeatherState,
    uiEvent: (UIEvent) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorSurfaceWeather)
        .safeDrawingPadding()
    ) {
        state.weatherInfo?.let { weatherInfo ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    IconButton(modifier = Modifier.then(Modifier.size(26.dp)), onClick = { navigator.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_fill1),
                            contentDescription = "Back",
                            tint = colorOnSurfaceWeather,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Weekly forecast",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorOnSurfaceWeather
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp), contentPadding = PaddingValues(bottom = 32.dp, top = 12.dp)) {
                        items(weatherInfo.dailyWeatherData) {weatherDataDay ->
                            ForecastForADay(weatherDataDay = weatherDataDay)
                        }
                    }
                }
            }
        }
        state.weatherError?.let { error ->
            ErrorBox(
                error = error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(colorOnSurfaceWeather),
                surfaceColor = colorSurfaceWeather,
                plainTextColor = colorPlainTextWeather,
                uiEvent = uiEvent
            )
            Log.i("Error to API request", error)
        }
    }
}

@Composable
fun ForecastForADay(
    weatherDataDay: List<WeatherData>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = weatherDataDay[0].time.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM")), style = MaterialTheme.typography.titleMedium, color = colorOnSurfaceWeather, modifier = Modifier.padding(horizontal = 32.dp))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 32.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(weatherDataDay) {weatherData ->
                if (weatherData.time.dayOfWeek == LocalDateTime.now().dayOfWeek) {
                    ForecastForAnHour(
                        weatherData = weatherData,
                        contentColor = colorSurfaceWeather,
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.medium)
                            .background(colorOnSurfaceWeather)
                    )
                } else {
                    ForecastForAnHour(
                        weatherData = weatherData,
                        contentColor = colorOnSurfaceWeather,
                        modifier = Modifier.border(width = 2.dp, color = colorOnSurfaceWeather, shape = MaterialTheme.shapes.medium)
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastForAnHour(
    weatherData: WeatherData,
    contentColor: Color,
    modifier: Modifier
) {
    Box(modifier = modifier
        .padding(8.dp)
        .width(100.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = timeFormat(time = weatherData.time), style = MaterialTheme.typography.bodyMedium, color = contentColor)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = weatherData.weatherType.iconRes),
                    contentDescription = weatherData.weatherType.weatherDesc,
                    tint = contentColor,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weatherData.temperatureCelsius.roundToInt()}°",
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_air_fill1),
                    contentDescription = "Wind speed",
                    tint = contentColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${weatherData.windSpeed.roundToInt()}m/s",
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_near_me_fill1),
                    contentDescription = weatherData.windDirection.direction,
                    tint = contentColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = weatherData.windDirection.direction,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_thermostat_fill1),
                    contentDescription = "Pressure",
                    tint = contentColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weatherData.pressure.roundToInt()}mmHg",
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = weatherData.humidityType.iconRes),
                    contentDescription = weatherData.humidityType.humidityDesc,
                    tint = contentColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weatherData.humidity}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
            }
        }
    }
}
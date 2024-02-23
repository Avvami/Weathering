package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.presentation.WeatherState
import com.personal.weathering.presentation.ui.theme.colorOnSurfaceWeather
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeeklyForecast(
    state: WeatherState
) {
    state.weatherInfo?.dailyWeatherData?.let { day ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Weekly forecast", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = colorOnSurfaceWeather)
            IconButton(modifier = Modifier.then(Modifier.size(26.dp)), onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward_fill0),
                    contentDescription = "View weekly forecast",
                    tint = colorOnSurfaceWeather,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 32.dp),
                content = {
//                    items(day) {weatherPerDay ->
//                        WeatherInDay(
//                            temperature = "${weatherPerDay.find { it.time.hour == 15 }?.temperatureCelsius?.roundToInt()}",
//                            imageId = weatherPerDay.find { it.time.hour == 15 }?.weatherType?.iconRes ?: R.drawable.ic_clear_day_fill1,
//                            imageDescription = weatherPerDay.find { it.time.hour == 15 }?.weatherType?.weatherDesc ?: "",
//                            date = weatherPerDay.find { it.time.hour == 15 }?.time?.format(DateTimeFormatter.ofPattern("dd EE")) ?: "",
//                            modifier = Modifier
//                                .width(96.dp)
//                                .border(
//                                    width = 2.dp,
//                                    color = colorOnSurfaceWeather,
//                                    shape = MaterialTheme.shapes.medium
//                                )
//                                .padding(horizontal = 24.dp, vertical = 16.dp)
//                        )
//                    }
                }
            )
        }
    }
}

@Composable
fun WeatherInDay(
    temperature: String,
    imageId: Int,
    imageDescription: String,
    date: String,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$temperature°",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = colorOnSurfaceWeather
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(id = imageId),
                contentDescription = imageDescription,
                modifier = Modifier.size(36.dp),
                tint = colorOnSurfaceWeather
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = colorOnSurfaceWeather
            )
        }
    }
}
package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.weathering.core.domain.models.TabItem
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.weather.presenation.WeatherState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerView(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    horizontalPagerState: PagerState,
    weatherState: WeatherState,
    tabItems: List<TabItem>
) {
    HorizontalPager(
        modifier = modifier,
        state = horizontalPagerState,
        verticalAlignment = Alignment.Top,
        key = { tabItems[it].dayOfMonth }
    ) { index ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            weatherState.weatherInfo?.dailyWeatherSummaryData?.get(index)?.takeIf { it.isNotEmpty() }?.let { dailyWeatherSummaryData ->
                item {
                    TemperaturePanel(
                        preferencesState = preferencesState,
                        summaryData = dailyWeatherSummaryData
                    )
                }
                item {
                    WindPanel(
                        preferencesState = preferencesState,
                        summaryData = dailyWeatherSummaryData
                    )
                }
                item {
                    PressurePanel(
                        preferencesState = preferencesState,
                        summaryData = dailyWeatherSummaryData
                    )
                }
                item {
                    HumidityPanel(
                        summaryData = dailyWeatherSummaryData
                    )
                }
                item {
                    DaylightPanel(
                        preferencesState = preferencesState,
                        dailyWeatherData = weatherState.weatherInfo.dailyWeatherData[index]
                    )
                }
            }
        }
    }
}
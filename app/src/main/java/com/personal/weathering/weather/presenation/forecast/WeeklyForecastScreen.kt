package com.personal.weathering.weather.presenation.forecast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.core.domain.models.TabItem
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.weather.presenation.WeatherState
import com.personal.weathering.weather.presenation.forecast.components.CustomScrollableTabRow
import com.personal.weathering.weather.presenation.forecast.components.ListView
import com.personal.weathering.weather.presenation.forecast.components.PagerView
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherDetailsScreen(
    windowInfo: () -> WindowInfo,
    preferencesState: State<PreferencesState>,
    weatherState: WeatherState,
    navigateBack: () -> Unit
) {
    val weeklyForecastViewModel: WeeklyForecastViewModel = viewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.weekly_forecast),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            val tabItems by remember {
                derivedStateOf {
                    buildList {
                        weatherState.weatherInfo?.dailyWeatherData?.map { dailyWeatherData ->
                            dailyWeatherData.time?.let { time ->
                                add(
                                    TabItem(
                                        time = time,
                                        dayOfMonth = time.dayOfMonth,
                                        dayOfWeek = time.format(DateTimeFormatter.ofPattern("EEE, d")).replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
            val horizontalPagerState = rememberPagerState(
                initialPage = weeklyForecastViewModel.selectedDayOfWeek,
                pageCount = { tabItems.size }
            )
            val lazyListState = rememberLazyListState(
                initialFirstVisibleItemIndex = weeklyForecastViewModel.selectedDayOfWeek
            )
            LaunchedEffect(key1 = weeklyForecastViewModel.selectedDayOfWeek) {
                horizontalPagerState.animateScrollToPage(weeklyForecastViewModel.selectedDayOfWeek)
            }
            LaunchedEffect(key1 = horizontalPagerState.currentPage, key2 = horizontalPagerState.targetPage) {
                if (horizontalPagerState.currentPage == horizontalPagerState.targetPage)
                    weeklyForecastViewModel.weatherDetailsUiEvent(
                        WeeklyForecastUiEvent.SetSelectedDayOfWeek(horizontalPagerState.currentPage)
                    )
            }
            if (windowInfo().screenWidthInfo !is WindowInfo.WindowType.Expanded) {
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    CustomScrollableTabRow(
                        selectedDayOfWeek = weeklyForecastViewModel::selectedDayOfWeek,
                        tabItems = { tabItems },
                        weatherDetailsUiEvent = weeklyForecastViewModel::weatherDetailsUiEvent
                    )
                }
            }
            if (windowInfo().screenWidthInfo !is WindowInfo.WindowType.Expanded) {
                PagerView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    preferencesState = preferencesState,
                    horizontalPagerState = horizontalPagerState,
                    weatherState = weatherState,
                    tabItems = tabItems
                )
            } else {
                ListView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    preferencesState = preferencesState,
                    lazyListState = lazyListState,
                    weatherState = weatherState,
                    tabItems = tabItems
                )
            }
        }
    }
}
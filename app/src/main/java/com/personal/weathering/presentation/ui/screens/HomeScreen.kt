@file:OptIn(ExperimentalFoundationApi::class)

package com.personal.weathering.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.ErrorBox
import com.personal.weathering.presentation.UIEvent
import com.personal.weathering.presentation.WeatherState
import com.personal.weathering.presentation.ui.screens.aq.AQScreen
import com.personal.weathering.presentation.ui.screens.weather.WeatherScreen
import com.personal.weathering.presentation.ui.theme.colorOnSurfaceAQ
import com.personal.weathering.presentation.ui.theme.colorOnSurfaceWeather
import com.personal.weathering.presentation.ui.theme.colorPlainTextAQ
import com.personal.weathering.presentation.ui.theme.colorPlainTextWeather
import com.personal.weathering.presentation.ui.theme.colorSurfaceAQ
import com.personal.weathering.presentation.ui.theme.colorSurfaceWeather
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    state: WeatherState,
    modifier: Modifier,
    uiEvent: (UIEvent) -> Unit
) {
    Box(modifier = modifier) {
        val pageCount = 2
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        WeatherScreen(
                            navigator = navigator,
                            state = state,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorSurfaceWeather)
                                .safeDrawingPadding()
                        )

                        state.weatherError?.let { error ->
                            ErrorBox(
                                error = error,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(32.dp)
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(state.onSurfaceColor),
                                surfaceColor = state.surfaceColor,
                                plainTextColor = state.plainTextColor,
                                uiEvent = uiEvent
                            )
                            Log.i("Error to API request", error)
                        }
                    }
                    1 -> {
                        AQScreen(
                            state = state,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorSurfaceAQ)
                                .safeDrawingPadding(),
                            uiEvent = uiEvent
                        )

                        state.aqError?.let { error ->
                            ErrorBox(
                                error = error,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(32.dp)
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(state.onSurfaceColor),
                                surfaceColor = state.surfaceColor,
                                plainTextColor = state.plainTextColor,
                                uiEvent = uiEvent
                            )
                            Log.i("Error to API request", error)
                        }
                    }
                }
            }
        }
        if (pagerState.currentPage == 0) {
            uiEvent(UIEvent.ChangeAccentColors(
                surfaceColor = colorSurfaceWeather,
                onSurfaceColor = colorOnSurfaceWeather,
                plainTextColor = colorPlainTextWeather
            ))
        } else if (pagerState.currentPage == 1) {
            uiEvent(UIEvent.ChangeAccentColors(
                surfaceColor = colorSurfaceAQ,
                onSurfaceColor = colorOnSurfaceAQ,
                plainTextColor = colorPlainTextAQ
            ))
        }
        HorizontalPagerIndicator(
            pageCount = pageCount,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .background(state.surfaceColor),
            color = state.onSurfaceColor
        )
        if (state.isLoading) {
            LoadingBox(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(state.onSurfaceColor)
                    .padding(8.dp),
                surfaceColor = state.surfaceColor
            )
        }
    }
}

@Composable
fun HorizontalPagerIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier,
    color: Color
) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        repeat(pageCount) { page ->
            val iconId = if (pagerState.currentPage == page) R.drawable.ic_fiber_manual_record_fill1 else R.drawable.ic_fiber_manual_record_fill0
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Horizontal pager indicator",
                modifier = Modifier.size(18.dp),
                tint = color
            )
        }
    }
}

@Composable
fun LoadingBox(
    modifier: Modifier,
    surfaceColor: Color
) {
    Box(modifier = modifier
        .padding(24.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(
                color = surfaceColor
            )
            Text(text = "Fetching data", style = MaterialTheme.typography.titleLarge, color = surfaceColor)
        }
    }
}
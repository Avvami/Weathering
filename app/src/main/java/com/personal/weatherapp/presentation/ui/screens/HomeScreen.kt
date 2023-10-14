@file:OptIn(ExperimentalFoundationApi::class)

package com.personal.weatherapp.presentation.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.weatherapp.R
import com.personal.weatherapp.presentation.ErrorBox
import com.personal.weatherapp.presentation.UIEvent
import com.personal.weatherapp.presentation.WeatherState
import com.personal.weatherapp.presentation.ui.screens.aq.AQScreen
import com.personal.weatherapp.presentation.ui.screens.weather.WeatherScreen
import com.personal.weatherapp.presentation.ui.theme.colorOnSurfaceAQ
import com.personal.weatherapp.presentation.ui.theme.colorOnSurfaceWeather
import com.personal.weatherapp.presentation.ui.theme.colorPlainTextAQ
import com.personal.weatherapp.presentation.ui.theme.colorPlainTextWeather
import com.personal.weatherapp.presentation.ui.theme.colorSurfaceAQ
import com.personal.weatherapp.presentation.ui.theme.colorSurfaceWeather
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
        val pagerState = rememberPagerState(initialPage = 0)
        var surfaceColor by remember { mutableStateOf(colorSurfaceWeather) }
        var onSurfaceColor by remember { mutableStateOf(colorOnSurfaceWeather) }
        var plainTextColor by remember { mutableStateOf(colorPlainTextWeather) }
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            HorizontalPager(
                pageCount = pageCount,
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

                        surfaceColor = colorSurfaceWeather
                        onSurfaceColor = colorOnSurfaceWeather
                        plainTextColor = colorPlainTextWeather

                        state.weatherError?.let { error ->
                            ErrorBox(
                                error = error,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(32.dp)
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(onSurfaceColor),
                                surfaceColor = surfaceColor,
                                plainTextColor = plainTextColor,
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

                        surfaceColor = colorSurfaceAQ
                        onSurfaceColor = colorOnSurfaceAQ
                        plainTextColor = colorPlainTextAQ

                        state.aqError?.let { error ->
                            ErrorBox(
                                error = error,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(32.dp)
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(onSurfaceColor),
                                surfaceColor = surfaceColor,
                                plainTextColor = plainTextColor,
                                uiEvent = uiEvent
                            )
                            Log.i("Error to API request", error)
                        }
                    }
                }
            }
        }
        HorizontalPagerIndicator(
            pageCount = pageCount,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .background(surfaceColor),
            color = onSurfaceColor
        )
        if (state.isLoading) {
            LoadingBox(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(onSurfaceColor)
                    .padding(8.dp),
                surfaceColor = surfaceColor
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
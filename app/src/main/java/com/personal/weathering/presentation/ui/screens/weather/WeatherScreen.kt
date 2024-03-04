package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.BuildConfig
import com.personal.weathering.R
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.weather.components.modal.ModalDrawer
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherDetails
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherTemperatureInfo
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherWeeklyForecast
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    currentCityState: State<CurrentCityState>,
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateToAqScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            uiEvent(UiEvent.LoadWeatherInfo(currentCityState.value.lat, currentCityState.value.lon))
        }
    }
    val scaleFraction = if (pullToRefreshState().isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState().progress).coerceIn(0f, 1f)

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawer(
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                currentCityState = currentCityState,
                weatherState = weatherState,
                uiEvent = uiEvent,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = currentCityState.value.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = weatheringDarkBlue,
                        titleContentColor = weatheringDarkBlue
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer", tint = weatheringDarkBlue)
                        }
                    },
                    actions = {
                        IconButton(onClick = navigateToSearchScreen) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search", tint = weatheringDarkBlue)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            },
            containerColor = weatheringBlue,
            contentColor = weatheringDarkBlue
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                AnimatedVisibility(visible = weatherState().isLoading) {
                    ThinLinearProgressIndicator()
                }
                Box(
                    modifier = if (weatherState().weatherInfo == null) {
                        Modifier
                            .fillMaxSize()
                            .nestedScroll(pullToRefreshState().nestedScrollConnection)
                            .verticalScroll(rememberScrollState())
                    } else {
                        Modifier.nestedScroll(pullToRefreshState().nestedScrollConnection)
                    }
                ) {
                    weatherState().error?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 24.dp)
                        )
                    }
                    weatherState().weatherInfo?.let { weatherInfo ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            item {
                                WeatherTemperatureInfo(
                                    preferencesState = preferencesState,
                                    weatherInfo = { weatherInfo }
                                )
                            }
                            item {
                                WeatherDetails(
                                    preferencesState = preferencesState,
                                    weatherInfo = { weatherInfo },
                                    aqState = aqState,
                                    navigateToAqScreen = navigateToAqScreen
                                )
                            }
                            item {
                                WeatherWeeklyForecast(
                                    preferencesState = preferencesState,
                                    weatherInfo = { weatherInfo }
                                )
                            }
                            item {
                                Text(
                                    text = stringResource(
                                        id = R.string.app_version,
                                        BuildConfig.VERSION_NAME,
                                        BuildConfig.VERSION_CODE
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = weatheringDarkBlue70p,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                )
                            }
                        }
                    }
                    PullToRefreshContainer(
                        modifier = Modifier.align(Alignment.TopCenter).graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
                        state = pullToRefreshState(),
                        containerColor = weatheringDarkBlue,
                        contentColor = weatheringBlue
                    )
                }
            }
        }
    }
}
package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherDetails
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherTemperatureInfo
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherWeeklyForecast
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    currentCityState: () -> CurrentCityState,
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    navigateToAqScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    val weatherViewModel: WeatherViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = { /**/ },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = currentCityState().name, fontWeight = FontWeight.Medium) },
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
                Box {
                    weatherState().error?.let { error ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .align(Alignment.TopCenter)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    weatherState().weatherInfo?.let { weatherInfo ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            item {
                                WeatherTemperatureInfo(
                                    weatherInfo = { weatherInfo }
                                )
                            }
                            item {
                                WeatherDetails(
                                    weatherInfo = { weatherInfo },
                                    aqState = aqState,
                                    navigateToAqScreen = navigateToAqScreen
                                )
                            }
                            item {
                                WeatherWeeklyForecast(
                                    weatherInfo = { weatherInfo }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
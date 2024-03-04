package com.personal.weathering.presentation.ui.screens.aq

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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.aq.components.AqThreeDayForecast
import com.personal.weathering.presentation.ui.screens.aq.components.CurrentAqInfo
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqScreen(
    currentCityState: State<CurrentCityState>,
    preferencesState: State<PreferencesState>,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateBack: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            uiEvent(UiEvent.UpdateAqInfo(currentCityState.value.lat, currentCityState.value.lon))
        }
    }
    val scaleFraction = if (pullToRefreshState().isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState().progress).coerceIn(0f, 1f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.aqi), fontWeight = FontWeight.Medium) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = weatheringDarkBlue,
                    titleContentColor = weatheringDarkBlue
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
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
            AnimatedVisibility(visible = aqState().isLoading) {
                ThinLinearProgressIndicator()
            }
            Box(
                modifier = if (aqState().aqInfo == null) {
                    Modifier
                        .fillMaxSize()
                        .nestedScroll(pullToRefreshState().nestedScrollConnection)
                        .verticalScroll(rememberScrollState())
                } else {
                    Modifier.nestedScroll(pullToRefreshState().nestedScrollConnection)
                }
            ) {
                aqState().error?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 24.dp)
                    )
                }
                aqState().aqInfo?.let { aqInfo ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            CurrentAqInfo(
                                preferencesState = preferencesState,
                                aqInfo = { aqInfo }
                            )
                        }
                        item {
                            AqThreeDayForecast(
                                preferencesState = preferencesState,
                                aqInfo = { aqInfo }
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
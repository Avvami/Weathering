package com.personal.weathering.presentation.ui.screens.aq

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.aq.components.AqThreeDayForecast
import com.personal.weathering.presentation.ui.screens.aq.components.CurrentAqInfo
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqScreen(
    aqState: () -> AqState,
    navigateBack: () -> Unit
) {
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
            Box {
                aqState().error?.let { error ->
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
                aqState().aqInfo?.let { aqInfo ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            CurrentAqInfo(
                                aqInfo = { aqInfo }
                            )
                        }
                        item {
                            AqThreeDayForecast(
                                aqInfo = { aqInfo }
                            )
                        }
                    }
                }
            }
        }
    }
}
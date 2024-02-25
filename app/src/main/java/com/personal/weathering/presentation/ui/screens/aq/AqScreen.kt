package com.personal.weathering.presentation.ui.screens.aq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.aq.components.AqiDetails
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                ) {
                                    Text(
                                        text = aqInfo.currentAqData.usAqi.toString(),
                                        fontSize = 82.sp
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.today_time, timeFormat(time = aqInfo.currentAqData.time)),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = stringResource(
                                                id = R.string.aqi_rate,
                                                stringResource(id = R.string.aqi),
                                                aqInfo.currentAqData.usAqiType.aqDesc
                                            ),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Icon(
                                    painter = painterResource(id = aqInfo.currentAqData.usAqiType.iconLargeRes),
                                    contentDescription = aqInfo.currentAqData.usAqiType.aqDesc,
                                    modifier = Modifier
                                        .padding(horizontal = 24.dp)
                                        .size(200.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                CompositionLocalProvider(
                                    LocalOverscrollConfiguration provides null
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .horizontalScroll(rememberScrollState())
                                            .padding(horizontal = 24.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AqiDetails(aqInfo.currentAqData.particulateMatter25, R.drawable.icon_pm2_5)
                                        AqiDetails(aqInfo.currentAqData.particulateMatter10, R.drawable.icon_pm10)
                                        AqiDetails(aqInfo.currentAqData.nitrogenDioxide, R.drawable.icon_no2)
                                        AqiDetails(aqInfo.currentAqData.ozone, R.drawable.icon_o3)
                                        AqiDetails(aqInfo.currentAqData.sulphurDioxide, R.drawable.icon_so2)
                                        AqiDetails(aqInfo.currentAqData.carbonMonoxide, R.drawable.icon_co, false)
                                    }
                                }
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.three_day_forecast),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    aqInfo.hourlyAqData.values.forEachIndexed { index, hourlyAqData ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(MaterialTheme.shapes.large)
                                                .background(weatheringDarkBlue3p)
                                                .padding(horizontal = 16.dp, vertical = 6.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = hourlyAqData[0].time.format(DateTimeFormatter.ofPattern("d MMMM")),
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = weatheringDarkBlue70p
                                                )
                                                Text(
                                                    text = when (index) {
                                                        0 -> stringResource(id = R.string.today)
                                                        1 -> stringResource(id = R.string.tomorrow)
                                                        else -> hourlyAqData[0].time.format(DateTimeFormatter.ofPattern("EEEE"))
                                                    },
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = weatheringDarkBlue
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Text(
                                                    text = hourlyAqData.maxBy { it.usAqi }.usAqiType.aqDesc,
                                                    style = MaterialTheme.typography.labelLarge
                                                )
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    if (index == 0) {
                                                        Text(
                                                            text = stringResource(id = R.string.max),
                                                            style = MaterialTheme.typography.labelMedium,
                                                            color = weatheringDarkBlue70p
                                                        )
                                                    }
                                                    Text(
                                                        text = hourlyAqData.maxBy { it.usAqi }.usAqi.toString(),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        fontWeight = FontWeight.Medium,
                                                        color = weatheringDarkBlue
                                                    )
                                                }
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    if (index == 0) {
                                                        Text(
                                                            text = stringResource(id = R.string.min),
                                                            style = MaterialTheme.typography.labelMedium,
                                                            color = weatheringDarkBlue70p
                                                        )
                                                    }
                                                    Text(
                                                        text = hourlyAqData.minBy { it.usAqi }.usAqi.toString(),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        fontWeight = FontWeight.Medium,
                                                        color = weatheringDarkBlue70p
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
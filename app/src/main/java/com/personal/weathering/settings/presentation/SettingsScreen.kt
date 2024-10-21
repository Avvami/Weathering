package com.personal.weathering.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.BuildConfig
import com.personal.weathering.MainActivity
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.C
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.core.util.findActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    windowInfo: () -> WindowInfo,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    val activity = LocalContext.current.findActivity() as MainActivity
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.data),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change temperature units*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_device_thermostat_fill0_wght400),
                                contentDescription = "Temperature",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.temperature_units),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = if (preferencesState.value.useCelsius) stringResource(id = R.string.celsius_unit) else
                                stringResource(id = R.string.fahrenheit_unit),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change wind units*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                                contentDescription = "Wind",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.wind_speed_units),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = if (preferencesState.value.useKmPerHour) stringResource(id = R.string.km_per_hour_unit) else
                                stringResource(id = R.string.m_per_second_unit),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change pressure units*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                                contentDescription = "Pressure",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.pressure_units),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = if (preferencesState.value.useHpa) stringResource(id = R.string.hPa_unit) else
                                stringResource(id = R.string.mmHg_unit),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change aqi*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
                                contentDescription = "Air Quality",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.air_quality_index),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = if (preferencesState.value.useUSaq) stringResource(id = R.string.us_aqi) else
                                stringResource(id = R.string.european_aqi),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.app),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { uiEvent(UiEvent.SetTimeFormat(use12hour = !preferencesState.value.use12hour)) }
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_chronic_fill0_wght400),
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.use_twelve_hour),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Switch(
                            checked = preferencesState.value.use12hour,
                            onCheckedChange = { uiEvent(UiEvent.SetTimeFormat(use12hour = !preferencesState.value.use12hour)) }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change appearance*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_palette_fill0_wght400),
                                contentDescription = "Appearance",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.appearance),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = when (preferencesState.value.isDark) {
                                true -> stringResource(id = R.string.dark)
                                false -> stringResource(id = R.string.light)
                                else -> stringResource(id = R.string.system)
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /*TODO: Change language*/ }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_language_japanese_kana_fill0_wght400),
                                contentDescription = "Language",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.language),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.current_language),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activity.openCustomWebTab(C.GITHUB) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_github_fill0),
                            contentDescription = "Github",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = stringResource(id = R.string.project_on_github),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_circle_fill0_wght400),
                                contentDescription = "Version",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Column {
                                Text(
                                    text = stringResource(id = R.string.app_version),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
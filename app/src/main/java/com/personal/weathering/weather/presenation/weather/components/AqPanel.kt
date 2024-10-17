package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.aq.domain.models.AqInfo
import com.personal.weathering.core.presentation.PreferencesState
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AqPanel(
    preferencesState: State<PreferencesState>,
    aqInfo: AqInfo,
    isCollapsed: () -> Boolean,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = R.string.aqi,
                    if (preferencesState.value.useUSaq) aqInfo.currentAqData.usAqiType.aqValue else aqInfo.currentAqData.euAqiType.aqValue
                ),
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = if (preferencesState.value.useUSaq) painterResource(id = aqInfo.currentAqData.usAqiType.iconFilledRes) else
                            painterResource(id = aqInfo.currentAqData.euAqiType.iconFilledRes),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.weight(weight = .5f, fill = false),
                        text = if (preferencesState.value.useUSaq) stringResource(id = aqInfo.currentAqData.usAqiType.aqIndexRes) else
                            stringResource(id = aqInfo.currentAqData.euAqiType.aqIndexRes),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                OutlinedIconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        uiEvent(UiEvent.ChangeAqCollapsedState)
                    },
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    val rotation by animateIntAsState(targetValue = if (isCollapsed()) 90 else -90, label = "Rotation animation")
                    Icon(
                        modifier = Modifier.rotate(rotation.toFloat()),
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Expand more"
                    )
                }
            }
        }
        AnimatedVisibility(visible = isCollapsed()) {
            Column(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = if (preferencesState.value.useUSaq) aqInfo.currentAqData.usAqiType.aqDescRes else aqInfo.currentAqData.euAqiType.aqDescRes),
                    style = MaterialTheme.typography.bodyMedium
                )
                with(aqInfo.currentAqData) {
                    buildList<@Composable FlowRowScope.() -> Unit> {
                        add {
                            particulateMatter25?.let { pm25 ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", pm25),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_pm2_5),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(14.dp)
                                    )
                                }
                            }
                        }
                        add {
                            particulateMatter10?.let { pm10 ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", pm10),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_pm10),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(14.dp)
                                    )
                                }
                            }
                        }
                        add {
                            nitrogenDioxide?.let { nitrogenDioxide ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", nitrogenDioxide),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_no2),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(14.dp)
                                    )
                                }
                            }
                        }
                        add {
                            ozone?.let { ozone ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", ozone),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_o3),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(14.dp)
                                    )
                                }
                            }
                        }
                        add {
                            sulphurDioxide?.let { sulphurDioxide ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", sulphurDioxide),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_so2),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(14.dp)
                                    )
                                }
                            }
                        }
                        add {
                            carbonMonoxide?.let { carbonMonoxide ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = String.format(Locale.getDefault(), "%.1f", carbonMonoxide),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_co),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface.copy(.7f),
                                        modifier = Modifier.height(12.dp)
                                    )
                                }
                            }
                        }
                    }.takeIf { it.isNotEmpty() }?.let { components ->
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            components.fastForEach { component ->
                                component()
                            }
                        }
                    }
                }
            }
        }
    }
}
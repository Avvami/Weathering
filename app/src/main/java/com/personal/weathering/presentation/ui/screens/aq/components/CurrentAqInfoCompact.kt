package com.personal.weathering.presentation.ui.screens.aq.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.screens.aq.AqUiEvent
import com.personal.weathering.presentation.ui.theme.onSurfaceLight

@Composable
fun CurrentAqInfoCompact(
    preferencesState: State<PreferencesState>,
    aqInfo: () -> AqInfo,
    aqDetailsExpanded: () -> Boolean,
    aqUiEvent: (AqUiEvent) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.weight(.45f),
                text = if (preferencesState.value.useUSaq) aqInfo().currentAqData.usAqiType.aqValue.toString() else aqInfo().currentAqData.euAqiType.aqValue.toString(),
                fontSize = 82.sp,
                color = onSurfaceLight
            )
            Column(
                modifier = Modifier.weight(.55f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.today_time, timeFormat(time = aqInfo().currentAqData.time)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = onSurfaceLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        id = R.string.aqi_rate,
                        stringResource(id = R.string.aqi),
                        if (preferencesState.value.useUSaq) stringResource(id = aqInfo().currentAqData.usAqiType.aqIndexRes) else
                            stringResource(id = aqInfo().currentAqData.euAqiType.aqIndexRes)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = onSurfaceLight
                )
            }
        }
        Icon(
            painter = if (preferencesState.value.useUSaq) painterResource(id = aqInfo().currentAqData.usAqiType.iconLargeRes) else
                painterResource(id = aqInfo().currentAqData.euAqiType.iconLargeRes),
            contentDescription = if (preferencesState.value.useUSaq) stringResource(id = aqInfo().currentAqData.usAqiType.aqIndexRes) else
                stringResource(id = aqInfo().currentAqData.euAqiType.aqIndexRes),
            tint = onSurfaceLight,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_format_quote_fill1_wght400),
                contentDescription = "Aq desc",
                tint = onSurfaceLight
            )
            Text(
                text = stringResource(id = if (preferencesState.value.useUSaq) aqInfo().currentAqData.usAqiType.aqDescRes else
                    aqInfo().currentAqData.euAqiType.aqDescRes),
                style = MaterialTheme.typography.titleMedium,
                color = onSurfaceLight
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedAqDetails(
            preferencesState = preferencesState,
            aqInfo = aqInfo,
            aqDetailsExpanded = aqDetailsExpanded
        )
        IconButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { aqUiEvent(AqUiEvent.SetAqDetailsExpanded) },
            colors = IconButtonDefaults.iconButtonColors(contentColor = onSurfaceLight)
        ) {
            val rotation by animateIntAsState(targetValue = if (aqDetailsExpanded()) 90 else -90, label = "Rotation animation")
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Expand more",
                modifier = Modifier.rotate(rotation.toFloat())
            )
        }
    }
}

@Composable
fun AnimatedAqDetails(
    preferencesState: State<PreferencesState>,
    aqInfo: () -> AqInfo,
    aqDetailsExpanded: () -> Boolean
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val aqiData by remember {
            mutableStateOf (
                if (aqInfo().currentAqData.time.hour == 23) aqInfo().hourlyAqData[1]?.get(0) else
                    aqInfo().hourlyAqData[0]?.find { it.time.isAfter(aqInfo().currentAqData.time) }
            )
        }
        AqDetail(
            modifier = Modifier.fillMaxWidth(),
            iconRes = R.drawable.icon_pm2_5,
            aqValue = aqInfo().currentAqData.particulateMatter25,
            aqiValue = if (preferencesState.value.useUSaq) aqiData?.usAqiParticulateMatter25Type?.aqValue else
                aqiData?.euAqiParticulateMatter25Type?.aqValue,
            aqiIndexRes = if (preferencesState.value.useUSaq) aqiData?.usAqiParticulateMatter25Type?.aqIndexRes else
                aqiData?.euAqiParticulateMatter25Type?.aqIndexRes
        )
        AqDetail(
            modifier = Modifier.fillMaxWidth(),
            iconRes = R.drawable.icon_pm10,
            aqValue = aqInfo().currentAqData.particulateMatter10,
            aqiValue = if (preferencesState.value.useUSaq) aqiData?.usAqiParticulateMatter10Type?.aqValue else
                aqiData?.euAqiParticulateMatter10Type?.aqValue,
            aqiIndexRes = if (preferencesState.value.useUSaq) aqiData?.usAqiParticulateMatter10Type?.aqIndexRes else
                aqiData?.euAqiParticulateMatter10Type?.aqIndexRes
        )
        AnimatedVisibility(visible = aqDetailsExpanded()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AqDetail(
                    modifier = Modifier.fillMaxWidth(),
                    iconRes = R.drawable.icon_no2,
                    aqValue = aqInfo().currentAqData.nitrogenDioxide,
                    aqiValue = if (preferencesState.value.useUSaq) aqiData?.usAqiNitrogenDioxideType?.aqValue else
                        aqiData?.euAqiNitrogenDioxideType?.aqValue,
                    aqiIndexRes = if (preferencesState.value.useUSaq) aqiData?.usAqiNitrogenDioxideType?.aqIndexRes else
                        aqiData?.euAqiNitrogenDioxideType?.aqIndexRes
                )
                AqDetail(
                    modifier = Modifier.fillMaxWidth(),
                    iconRes = R.drawable.icon_o3,
                    aqValue = aqInfo().currentAqData.ozone,
                    aqiValue = if (preferencesState.value.useUSaq) aqiData?.usAqiOzoneType?.aqValue else
                        aqiData?.euAqiOzoneType?.aqValue,
                    aqiIndexRes = if (preferencesState.value.useUSaq) aqiData?.usAqiOzoneType?.aqIndexRes else
                        aqiData?.euAqiOzoneType?.aqIndexRes
                )
                AqDetail(
                    modifier = Modifier.fillMaxWidth(),
                    iconRes = R.drawable.icon_so2,
                    aqValue = aqInfo().currentAqData.sulphurDioxide,
                    aqiValue = if (preferencesState.value.useUSaq) aqiData?.usAqiSulphurDioxideType?.aqValue else
                        aqiData?.euAqiSulphurDioxideType?.aqValue,
                    aqiIndexRes = if (preferencesState.value.useUSaq) aqiData?.usAqiSulphurDioxideType?.aqIndexRes else
                        aqiData?.euAqiSulphurDioxideType?.aqIndexRes
                )
                if (preferencesState.value.useUSaq)
                    AqDetail(
                        modifier = Modifier.fillMaxWidth(),
                        iconRes = R.drawable.icon_co,
                        aqValue = aqInfo().currentAqData.carbonMonoxide,
                        aqiValue = aqiData?.usAqiCarbonMonoxideType?.aqValue,
                        aqiIndexRes = aqiData?.usAqiCarbonMonoxideType?.aqIndexRes
                    )
            }
        }
    }
}
package com.personal.weathering.aq.presentation.components.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.aq.presentation.AqUiEvent
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousPrimary
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousSecondary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodPrimary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodSecondary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthySecondary
import com.personal.weathering.ui.theme.aqiEuUsModeratePrimary
import com.personal.weathering.ui.theme.aqiEuUsModerateSecondary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthySecondary
import com.personal.weathering.ui.theme.aqiUsSensitivePrimary
import com.personal.weathering.ui.theme.aqiUsSensitiveSecondary

@Composable
fun AqUsScale(
    isUsAqScaleExpanded: () -> Boolean,
    aqUiEvent: (AqUiEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                aqUiEvent(AqUiEvent.SetUsAqScaleExpanded)
            }
            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.us_aq_scale),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            val rotation by animateIntAsState(targetValue = if (isUsAqScaleExpanded()) 90 else -90, label = "Rotation animation")
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Expand more",
                modifier = Modifier.rotate(rotation.toFloat())
            )
        }
    }
    AnimatedVisibility(
        visible = isUsAqScaleExpanded(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.good),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_good_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiEuFairUsGoodPrimary, aqiEuFairUsGoodSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_good_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.moderate),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_moderate_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiEuUsModeratePrimary, aqiEuUsModerateSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_moderate_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.unhealthy_for_sensitive),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_unhealthy_sensitive_groups_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiUsSensitivePrimary, aqiUsSensitiveSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_unhealthy_sensitive_groups_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.unhealthy),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_unhealthy_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiEuPoorUsUnhealthyPrimary, aqiEuPoorUsUnhealthySecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_unhealthy_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.very_unhealthy),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_very_unhealthy_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiEuVeryPoorUsVeryUnhealthyPrimary, aqiEuVeryPoorUsVeryUnhealthySecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_very_unhealthy_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.hazardous),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.us_hazardous_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiEuExtremelyPoorUsHazardousPrimary, aqiEuExtremelyPoorUsHazardousSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.us_hazardous_aqi_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.personal.weathering.presentation.ui.screens.aq.components.bottom_sheet

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
import com.personal.weathering.presentation.ui.screens.aq.AqUiEvent
import com.personal.weathering.presentation.ui.theme.aqiGoodPrimary
import com.personal.weathering.presentation.ui.theme.aqiGoodSecondary
import com.personal.weathering.presentation.ui.theme.aqiHazardousPrimary
import com.personal.weathering.presentation.ui.theme.aqiHazardousSecondary
import com.personal.weathering.presentation.ui.theme.aqiModeratePrimary
import com.personal.weathering.presentation.ui.theme.aqiModerateSecondary
import com.personal.weathering.presentation.ui.theme.aqiUnhealthyPrimary
import com.personal.weathering.presentation.ui.theme.aqiUnhealthySecondary
import com.personal.weathering.presentation.ui.theme.aqiVeryUnhealthyPrimary
import com.personal.weathering.presentation.ui.theme.aqiVeryUnhealthySecondary

@Composable
fun AqEuScale(
    isEuAqScaleExpanded: () -> Boolean,
    aqUiEvent: (AqUiEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                aqUiEvent(AqUiEvent.SetEuAqScaleExpanded)
            }
            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.eu_aq_scale),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            val rotation by animateIntAsState(targetValue = if (isEuAqScaleExpanded()) 90 else -90, label = "Rotation animation")
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Expand more",
                modifier = Modifier.rotate(rotation.toFloat())
            )
        }
    }
    AnimatedVisibility(
        visible = isEuAqScaleExpanded(),
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
                        text = stringResource(id = R.string.eu_good_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiGoodPrimary, aqiGoodSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_good_aqi_general_description),
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
                        text = stringResource(id = R.string.fair),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.eu_fair_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiGoodPrimary, aqiGoodSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_fair_aqi_general_description),
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
                        text = stringResource(id = R.string.eu_moderate_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiModeratePrimary, aqiModerateSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_moderate_aqi_general_description),
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
                        text = stringResource(id = R.string.poor),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.eu_poor_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiUnhealthyPrimary, aqiUnhealthySecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_poor_aqi_general_description),
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
                        text = stringResource(id = R.string.very_poor),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.eu_very_poor_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiVeryUnhealthyPrimary, aqiVeryUnhealthySecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_very_poor_aqi_general_description),
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
                        text = stringResource(id = R.string.extremely_poor),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(id = R.string.eu_extremely_poor_aqi_range),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(64.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(aqiHazardousPrimary, aqiHazardousSecondary)),
                            shape = MaterialTheme.shapes.large
                        )
                )
                Text(
                    text = stringResource(id = R.string.eu_extremely_poor_aqi_general_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.personal.weathering.presentation.ui.screens.aq.components.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.ui.screens.aq.AqUiEvent

@Composable
fun AqPollutants(
    isPollutantsExpanded: () -> Boolean,
    aqUiEvent: (AqUiEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                aqUiEvent(AqUiEvent.SetPollutantsExpanded)
            }
            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.pollutants),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            val rotation by animateIntAsState(targetValue = if (isPollutantsExpanded()) 90 else -90, label = "Rotation animation")
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Expand more",
                modifier = Modifier.rotate(rotation.toFloat())
            )
        }
    }
    AnimatedVisibility(
        visible = isPollutantsExpanded(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_pm2_5),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.pm25_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_pm10),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.pm10_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_no2),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.no2_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_o3),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.o3_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_so2),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.so2_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(top = 4.dp).weight(.1f),
                    painter = painterResource(id = R.drawable.icon_co),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(.9f),
                    text = stringResource(id = R.string.co_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.personal.weathering.aq.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.ui.theme.onSurfaceLight
import com.personal.weathering.ui.theme.surfaceLight30p

@Composable
fun AqDetail(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    aqValue: Double,
    @StringRes aqiValue: Int?,
    @StringRes aqiIndexRes: Int?
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(surfaceLight30p)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "aqiDetail",
                tint = onSurfaceLight,
                modifier = Modifier.height(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.aq_units, aqValue),
                style = MaterialTheme.typography.bodyMedium,
                color = onSurfaceLight
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        if (aqiValue != null && aqiIndexRes != null) {
            Text(
                text = stringResource(
                    id = R.string.aqi_rate,
                    aqiValue.toString(),
                    stringResource(id = aqiIndexRes),
                ),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleMedium,
                color = onSurfaceLight
            )
        }
    }
}
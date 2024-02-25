package com.personal.weathering.presentation.ui.screens.aq.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.R

@Composable
fun AqiDetails(
    data: Double,
    @DrawableRes iconRes: Int,
    showDivider: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "%.1f".format(data),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "aqiDetail",
                modifier = Modifier.height(20.dp)
            )
        }
        if (showDivider) {
            Icon(
                painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                contentDescription = "Divider",
                modifier = Modifier.padding(horizontal = 12.dp).size(8.dp)
            )
        }
    }
}
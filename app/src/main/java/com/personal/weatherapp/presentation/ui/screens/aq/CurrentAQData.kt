package com.personal.weatherapp.presentation.ui.screens.aq

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weatherapp.R
import com.personal.weatherapp.presentation.WeatherState
import com.personal.weatherapp.presentation.ui.theme.*
import java.time.format.DateTimeFormatter

@Composable
fun CurrentAQData(
    state: WeatherState,
    modifier: Modifier
) {
    state.aqInfo?.currentAQData?.let { data ->
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Krasnoyarsk",
                color = colorOnSurfaceAQ,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(colorOnSurfaceAQ)
                    .padding(12.dp, 4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = data.time.format(DateTimeFormatter.ofPattern("EEEE dd, MM")),
                    color = colorSurfaceAQ,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_aq_fill0),
                contentDescription = "Air quality",
                tint = colorOnSurfaceAQ,
                modifier = Modifier.size(180.dp).align(Alignment.CenterHorizontally)
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = data.aqType.aqDesc,
                    style = MaterialTheme.typography.headlineLarge,
                    color = colorOnSurfaceAQ
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = data.aqType.iconRes),
                    contentDescription = data.aqType.aqDesc,
                    tint = colorOnSurfaceAQ,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
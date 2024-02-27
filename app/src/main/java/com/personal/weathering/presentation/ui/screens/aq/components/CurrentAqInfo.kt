package com.personal.weathering.presentation.ui.screens.aq.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.util.timeFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentAqInfo(
    aqInfo: () -> AqInfo
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = aqInfo().currentAqData.usAqi.toString(),
                fontSize = 82.sp
            )
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.today_time, timeFormat(time = aqInfo().currentAqData.time)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        id = R.string.aqi_rate,
                        stringResource(id = R.string.aqi),
                        stringResource(id = aqInfo().currentAqData.usAqiType.aqDescRes)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = aqInfo().currentAqData.usAqiType.iconLargeRes),
            contentDescription = stringResource(id = aqInfo().currentAqData.usAqiType.aqDescRes),
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
                AqDetail(aqInfo().currentAqData.particulateMatter25, R.drawable.icon_pm2_5)
                AqDetail(aqInfo().currentAqData.particulateMatter10, R.drawable.icon_pm10)
                AqDetail(aqInfo().currentAqData.nitrogenDioxide, R.drawable.icon_no2)
                AqDetail(aqInfo().currentAqData.ozone, R.drawable.icon_o3)
                AqDetail(aqInfo().currentAqData.sulphurDioxide, R.drawable.icon_so2)
                AqDetail(aqInfo().currentAqData.carbonMonoxide, R.drawable.icon_co, false)
            }
        }
    }
}
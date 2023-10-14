package com.personal.weatherapp.presentation.ui.screens.aq

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weatherapp.R
import com.personal.weatherapp.presentation.WeatherState
import com.personal.weatherapp.presentation.ui.theme.*
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CurrentAQData(
    state: WeatherState,
    modifier: Modifier,
    openAlertDialog: () -> Unit,
    closeAlertDialog: () -> Unit
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
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
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
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(colorOnSurfaceAQ)
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "μg/m³",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorSurfaceAQ,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Box(modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = colorSurfaceAQ,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AQDetails(resourceId = R.drawable.ic_pm2_5, value = "${data.particulateMatter25?.roundToInt()}", description = "Particulate\nMatter 2.5")
                            AQDetails(resourceId = R.drawable.ic_pm10, value = "${data.particulateMatter10?.roundToInt()}", description = "Particulate\nMatter 10")
                            AQDetails(resourceId = R.drawable.ic_co, value = "${data.carbonMonoxide?.roundToInt()}", description = "Carbon\nMonoxide")
                            AQDetails(resourceId = R.drawable.ic_no2, value = "${data.nitrogenDioxide?.roundToInt()}", description = "Nitrogen\nDioxide")
                            AQDetails(resourceId = R.drawable.ic_so2, value = "${data.sulphurDioxide?.roundToInt()}", description = "Sulphur\nDioxide")
                        }
                    }
                }
            }
            IconButton(
                onClick = { openAlertDialog() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_fill1),
                    contentDescription = "Show info",
                    tint = colorOnSurfaceAQ
                )
            }
            state.openAlertDialog.let {
                if (it) {
                    InfoAlertDialog(
                        onDismissRequest = { closeAlertDialog() },
                        onConfirmation = { closeAlertDialog() },
                        dialogTitle = "Info",
                        dialogTextResId = R.string.info_dialog_text
                    )
                }
            }
        }
    }
}

@Composable
fun AQDetails(
    resourceId: Int,
    value: String,
    description: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = description,
            tint = colorSurfaceAQ,
            modifier = Modifier.height(36.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = colorSurfaceAQ
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 12.sp,
            color = colorSurfaceAQ,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InfoAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogTextResId: Int
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = stringResource(id = dialogTextResId), modifier = Modifier.height(230.dp).verticalScroll(rememberScrollState()))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = "Ok", color = colorSurfaceAQ)
            }
        },
        containerColor = colorOnSurfaceAQ,
        textContentColor = colorPlainTextAQ,
        titleContentColor = colorPlainTextAQ,
    )
}
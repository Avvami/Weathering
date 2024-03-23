package com.personal.weathering.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.personal.weathering.R
import com.personal.weathering.presentation.ui.theme.onErrorContainerLight

@Composable
fun NetworkConnection(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.offline),
        color = onErrorContainerLight,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}
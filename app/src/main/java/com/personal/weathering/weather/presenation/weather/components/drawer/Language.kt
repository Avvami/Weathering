package com.personal.weathering.weather.presenation.weather.components.drawer

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.personal.weathering.R
import com.personal.weathering.core.domain.models.DropdownItem
import com.personal.weathering.core.presentation.components.CustomDropdownMenu

@Composable
fun Language(
    modifier: Modifier = Modifier
) {
    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.ru to "ru"
    ).mapKeys { stringResource(it.key) }
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.language), style = MaterialTheme.typography.titleMedium)
        OutlinedButton(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
        ) {
            val rotation by animateIntAsState(targetValue = if (expanded) 90 else -90, label = "Rotation animation")
            Icon(
                modifier = Modifier.size(18.dp).rotate(rotation.toFloat()),
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.current_language))
            CustomDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                dropDownItems = localeOptions.keys.map { selectionLocale ->
                    DropdownItem(
                        iconRes = R.drawable.icon_done_all_fill0_wght400,
                        text = selectionLocale,
                        selected = selectionLocale == stringResource(id = R.string.current_language),
                        onItemClick = {
                            expanded = false
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    localeOptions[selectionLocale]
                                )
                            )
                        }
                    )
                }
            )
        }
    }
}
package com.personal.weathering.settings.presentation.components

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.core.presentation.PreferencesState

@Composable
fun App(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.app),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { uiEvent(UiEvent.SetTimeFormat(use12hour = !preferencesState.value.use12hour)) }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_chronic_fill0_wght400),
                    contentDescription = "Time",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.use_twelve_hour),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = preferencesState.value.use12hour,
                onCheckedChange = { uiEvent(UiEvent.SetTimeFormat(use12hour = !preferencesState.value.use12hour)) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    uiEvent(
                        UiEvent.ShowDialog(
                            content = {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetDarkMode(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = !preferencesState.value.isDark,
                                            onClick = {
                                                uiEvent(UiEvent.SetDarkMode(false))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.light))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                uiEvent(UiEvent.SetDarkMode(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = preferencesState.value.isDark,
                                            onClick = {
                                                uiEvent(UiEvent.SetDarkMode(true))
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.dark))
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                /*uiEvent(UiEvent.SetDarkMode(null)) TODO: Set null for system*/
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = preferencesState.value.isDark == null,
                                            onClick = {
                                                /*uiEvent(UiEvent.SetDarkMode(null)) TODO: Set null for system*/
                                                uiEvent(UiEvent.CloseDialog)
                                            }
                                        )
                                        Text(text = stringResource(id = R.string.system))
                                    }
                                }
                            },
                            confirmTextRes = R.string.cancel
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_palette_fill0_wght400),
                    contentDescription = "Appearance",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.appearance),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = when (preferencesState.value.isDark) {
                    true -> stringResource(id = R.string.dark)
                    false -> stringResource(id = R.string.light)
                    else -> stringResource(id = R.string.system)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
        val localeOptions = mapOf(
            R.string.en to "en",
            R.string.ru to "ru"
        ).mapKeys { stringResource(it.key) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    uiEvent(
                        UiEvent.ShowDialog(
                            content = {
                                Column(
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    localeOptions.keys.map { selectionLocale ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    AppCompatDelegate.setApplicationLocales(
                                                        LocaleListCompat.forLanguageTags(
                                                            localeOptions[selectionLocale]
                                                        )
                                                    )
                                                    uiEvent(UiEvent.CloseDialog)
                                                }
                                                .padding(horizontal = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = selectionLocale == stringResource(id = R.string.current_language),
                                                onClick = {
                                                    AppCompatDelegate.setApplicationLocales(
                                                        LocaleListCompat.forLanguageTags(
                                                            localeOptions[selectionLocale]
                                                        )
                                                    )
                                                    uiEvent(UiEvent.CloseDialog)
                                                }
                                            )
                                            Text(text = selectionLocale)
                                        }
                                    }
                                }
                            },
                            confirmTextRes = R.string.cancel
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_language_japanese_kana_fill0_wght400),
                    contentDescription = "Language",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = stringResource(id = R.string.current_language),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
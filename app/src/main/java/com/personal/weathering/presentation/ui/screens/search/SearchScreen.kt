package com.personal.weathering.presentation.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.WeatheringApp
import com.personal.weathering.domain.models.DropdownItem
import com.personal.weathering.domain.models.search.SearchLanguage
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.components.CustomDropdownMenu
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.search.components.SearchHistory
import com.personal.weathering.presentation.ui.screens.search.components.SearchResults
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import com.personal.weathering.presentation.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    navigateBack: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val searchViewModel: SearchViewModel = viewModel(
        factory = viewModelFactory {
            SearchViewModel(
                searchRepository = WeatheringApp.appModule.searchRepository,
                localRepository = WeatheringApp.appModule.localRepository
            )
        }
    )
    val customTextSelectionColors = TextSelectionColors(
        handleColor = weatheringDarkBlue,
        backgroundColor = weatheringDarkBlue.copy(alpha = .2f)
    )
    Scaffold(
        contentColor = weatheringDarkBlue,
        containerColor = weatheringBlue
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            SearchBar(
                query = searchViewModel.searchQuery,
                onQueryChange = { searchViewModel.searchUiEvent(SearchUiEvent.OnSearchQueryChange(it, preferencesState.value.searchLanguageCode)) },
                onSearch = { searchViewModel.searchUiEvent(SearchUiEvent.OnSearchQueryChange(it, preferencesState.value.searchLanguageCode)) },
                active = searchViewModel.searchFieldActive,
                onActiveChange = { if (!it) navigateBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(
                    containerColor = weatheringDarkBlue3p,
                    dividerColor = weatheringDarkBlue,
                    inputFieldColors = SearchBarDefaults.inputFieldColors(
                        focusedTextColor = weatheringDarkBlue,
                        cursorColor = weatheringDarkBlue,
                        focusedLeadingIconColor = weatheringDarkBlue,
                        focusedPlaceholderColor = weatheringDarkBlue70p,
                        focusedTrailingIconColor = weatheringDarkBlue,
                        unfocusedLeadingIconColor = weatheringDarkBlue70p,
                        unfocusedPlaceholderColor = weatheringDarkBlue70p,
                        unfocusedTextColor = weatheringDarkBlue70p,
                        unfocusedTrailingIconColor = weatheringDarkBlue70p
                    )
                ),
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                leadingIcon = {
                    IconButton(onClick = navigateBack, modifier = Modifier.padding(horizontal = 8.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        AnimatedVisibility(
                            visible = searchViewModel.searchQuery.isNotEmpty(),
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            IconButton(
                                onClick = { searchViewModel.searchUiEvent(SearchUiEvent.OnSearchQueryChange("", preferencesState.value.searchLanguageCode)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                        IconButton(
                            onClick = { searchViewModel.searchUiEvent(SearchUiEvent.SetLanguageDropdownExpanded(true)) }
                        ) {
                            Icon(painter = painterResource(id = R.drawable.icon_language_fill0_wght400), contentDescription = "Language")
                            CustomDropdownMenu(
                                expanded = searchViewModel.isLanguageDropdownExpanded,
                                onDismissRequest = {
                                    searchViewModel.searchUiEvent(SearchUiEvent.SetLanguageDropdownExpanded(false))
                                },
                                dropDownItems = SearchLanguage.languages.map {
                                    DropdownItem(
                                        iconRes = R.drawable.icon_done_all_fill0_wght400,
                                        textRes = it.name,
                                        selected = preferencesState.value.searchLanguageCode == it.code,
                                        onItemClick = {
                                            uiEvent(UiEvent.SetSearchLanguage(it.code))
                                            searchViewModel.searchUiEvent(SearchUiEvent.SetSearchLanguage(it.code))
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                AnimatedVisibility(visible = searchViewModel.searchState.isLoading) {
                    ThinLinearProgressIndicator()
                }
                AnimatedVisibility(
                    visible = searchViewModel.searchState.searchInfo != null,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    SearchResults(
                        favoritesState = favoritesState,
                        searchState = searchViewModel::searchState,
                        navigateBack = navigateBack,
                        setCurrentCityState = { cityId, city, lat, lon ->
                            uiEvent(UiEvent.SetCurrentCityState(
                                cityId, city, lat, lon
                            ))
                        },
                        removeFavorite = { id, cityId, city, lat, lon ->
                            uiEvent(UiEvent.RemoveFavorite(
                                id, cityId, city, lat, lon
                            ))
                        },
                        addFavorite = { cityId, city, lat, lon ->
                            uiEvent(UiEvent.AddFavorite(
                                cityId, city, lat, lon
                            ))
                        },
                        addToHistory = { cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(SearchUiEvent.AddToHistory(
                                cityId, city, lat, lon
                            ))
                        }
                    )
                }
                AnimatedVisibility(
                    visible = searchViewModel.searchState.searchInfo == null,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    SearchHistory(
                        favoritesState = favoritesState,
                        searchHistoryState = searchViewModel.searchHistoryState.collectAsState(),
                        navigateBack = navigateBack,
                        setCurrentCityState = { cityId, city, lat, lon ->
                            uiEvent(UiEvent.SetCurrentCityState(
                                cityId, city, lat, lon
                            ))
                        },
                        removeFavorite = { id, cityId, city, lat, lon ->
                            uiEvent(UiEvent.RemoveFavorite(
                                id, cityId, city, lat, lon
                            ))
                        },
                        addFavorite = { cityId, city, lat, lon ->
                            uiEvent(UiEvent.AddFavorite(
                                cityId, city, lat, lon
                            ))
                        },
                        removeFromHistory = { id, cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(SearchUiEvent.RemoveFromHistory(
                                id, cityId, city, lat, lon
                            ))
                        },
                        addToHistory = { cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(SearchUiEvent.AddToHistory(
                                cityId, city, lat, lon
                            ))
                        }
                    )
                }
            }
        }
    }
}
package com.personal.weathering.search.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.WeatheringApp
import com.personal.weathering.core.domain.models.DropdownItem
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.presentation.components.CustomDropdownMenu
import com.personal.weathering.search.domain.models.SearchLanguage
import com.personal.weathering.search.presentation.components.SearchHistory
import com.personal.weathering.search.presentation.components.SearchResults
import com.personal.weathering.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchViewModel.searchQuery,
                onValueChange = {
                    searchViewModel.searchUiEvent(
                        SearchUiEvent.OnSearchQueryChange(
                            query = it,
                            languageCode = preferencesState.value.searchLanguageCode
                        )
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_placeholder))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    Row {
                        AnimatedVisibility(
                            visible = searchViewModel.searchQuery.isNotEmpty(),
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            IconButton(
                                onClick = {
                                    searchViewModel.searchUiEvent(
                                        SearchUiEvent.OnSearchQueryChange(
                                            query = "",
                                            languageCode = preferencesState.value.searchLanguageCode
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                searchViewModel.searchUiEvent(SearchUiEvent.SetLanguageDropdownExpanded(true))
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_language_fill0_wght400),
                                contentDescription = "Language"
                            )
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
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            AnimatedContent(
                targetState = searchViewModel.searchState.searchInfo == null && !searchViewModel.searchState.isLoading && searchViewModel.searchState.error == null,
                label = "Search screen content animation"
            ) { targetState ->
                if (targetState) {
                    SearchHistory(
                        favoritesState = favoritesState,
                        searchHistoryState = searchViewModel.searchHistoryState.collectAsStateWithLifecycle(),
                        navigateBack = navigateBack,
                        setCurrentCityState = { cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.SetSelectedCity(
                                    cityId, city, lat, lon
                                ))
                        },
                        removeFavorite = { id, cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.RemoveFavorite(
                                    id, cityId, city, lat, lon
                                ))
                        },
                        addFavorite = { cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.AddFavorite(
                                    cityId, city, lat, lon
                                ))
                        },
                        removeFromHistory = { id, cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(
                                SearchUiEvent.RemoveFromHistory(
                                    id, cityId, city, lat, lon
                                )
                            )
                        },
                        addToHistory = { cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(
                                SearchUiEvent.AddToHistory(
                                    cityId, city, lat, lon
                                )
                            )
                        }
                    )
                } else {
                    SearchResults(
                        favoritesState = favoritesState,
                        searchState = searchViewModel::searchState,
                        navigateBack = navigateBack,
                        setCurrentCityState = { cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.SetSelectedCity(
                                    cityId, city, lat, lon
                                ))
                        },
                        removeFavorite = { id, cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.RemoveFavorite(
                                    id, cityId, city, lat, lon
                                ))
                        },
                        addFavorite = { cityId, city, lat, lon ->
                            uiEvent(
                                UiEvent.AddFavorite(
                                    cityId, city, lat, lon
                                ))
                        },
                        addToHistory = { cityId, city, lat, lon ->
                            searchViewModel.searchUiEvent(
                                SearchUiEvent.AddToHistory(
                                    cityId, city, lat, lon
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
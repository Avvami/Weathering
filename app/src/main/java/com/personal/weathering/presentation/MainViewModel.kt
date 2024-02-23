package com.personal.weathering.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.domain.airquality.AQInfo
import com.personal.weathering.domain.location.LocationTracker
import com.personal.weathering.domain.repository.AqRepository
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.presentation.state.MessageDialogState
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val aqRepository: AqRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    var messageDialogState by mutableStateOf(MessageDialogState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                weatherError = null,
                aqError = null
            )

            var weatherInfo: WeatherInfo? = null
            var aqInfo: AQInfo? = null
            var weatherError: String? = null
            var aqError: String? = null

            locationTracker.getCurrentLocation()?.let { location ->
                weatherRepository.getWeatherData(location.latitude, location.longitude).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            weatherError = result.message
                        }
                        is Resource.Success -> {
                            weatherInfo = result.data
                        }
                    }
                }

                aqRepository.getAQData(location.latitude, location.longitude).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            aqError = result.message
                        }
                        is Resource.Success -> {
                            aqInfo = result.data
                        }
                    }
                }

                state = state.copy(
                    weatherInfo = weatherInfo,
                    aqInfo = aqInfo,
                    isLoading = false,
                    weatherError = weatherError,
                    aqError = aqError
                )
            } ?: kotlin.run {
                weatherRepository.getWeatherData(56.0184, 92.8672).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            weatherError = result.message
                        }
                        is Resource.Success -> {
                            weatherInfo = result.data
                        }
                    }
                }

                aqRepository.getAQData(56.0184, 92.8672).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            aqError = result.message
                        }
                        is Resource.Success -> {
                            aqInfo = result.data
                        }
                    }
                }

                state = state.copy(
                    weatherInfo = weatherInfo,
                    aqInfo = aqInfo,
                    isLoading = false,
                    weatherError = weatherError,
                    aqError = aqError
                )
            }
        }
    }

    fun uiEvent(event: UiEvent) {
        when(event) {
            UiEvent.LoadWeatherInfo -> {
                loadWeatherInfo()
            }
            is UiEvent.ChangeAccentColors -> {
                state = state.copy(
                    surfaceColor = event.surfaceColor,
                    onSurfaceColor = event.onSurfaceColor,
                    plainTextColor = event.plainTextColor
                )
            }
            is UiEvent.ShowMessageDialog -> {
                messageDialogState = messageDialogState.copy(
                    isShown = true,
                    iconRes = event.iconRes,
                    titleRes = event.titleRes,
                    messageRes = event.messageRes,
                    messageString = event.messageString,
                    dismissTextRes = event.dismissTextRes,
                    onDismissRequest = { uiEvent(UiEvent.CloseMessageDialog) },
                    onDismiss = event.onDismiss,
                    confirmTextRes = event.confirmTextRes,
                    onConfirm = event.onConfirm ?: { uiEvent(UiEvent.CloseMessageDialog) }
                )
            }
            UiEvent.CloseMessageDialog -> { messageDialogState = messageDialogState.copy(isShown = false) }
        }
    }
}
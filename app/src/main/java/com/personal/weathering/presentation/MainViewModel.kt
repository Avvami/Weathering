package com.personal.weathering.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.domain.location.LocationTracker
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.repository.AqRepository
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.MessageDialogState
import com.personal.weathering.presentation.state.WeatherState
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val aqRepository: AqRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var weatherState by mutableStateOf(WeatherState())
        private set

    var aqState by mutableStateOf(AqState())
        private set

    var messageDialogState by mutableStateOf(MessageDialogState())
        private set

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            weatherState = weatherState.copy(
                isLoading = true,
                error = null
            )

            var weatherInfo: WeatherInfo? = null
            var error: String? = null

            weatherRepository.getWeatherData(56.0184, 92.8672).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        weatherInfo = result.data
                    }
                }
            }

            weatherState = weatherState.copy(
                weatherInfo = weatherInfo,
                isLoading = false,
                error = error
            )

            locationTracker.getCurrentLocation()?.let {} ?: kotlin.run {}
        }
    }

    private fun loadAqInfo() {
        viewModelScope.launch {
            aqState = aqState.copy(
                isLoading = true,
                error = null
            )

            var aqInfo: AqInfo? = null
            var error: String? = null

            aqRepository.getAqData(56.0184, 92.8672).let { result ->
                when (result) {
                    is Resource.Error -> {
                        error = result.message
                    }
                    is Resource.Success -> {
                        aqInfo = result.data
                    }
                }
            }

            aqState = aqState.copy(
                aqInfo = aqInfo,
                isLoading = false,
                error = error
            )
        }
    }

    fun uiEvent(event: UiEvent) {
        when(event) {
            UiEvent.LoadWeatherInfo -> {
                loadWeatherInfo()
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
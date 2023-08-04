package com.arjun.weather.presentation.detail

import com.arjun.core.state_management.UiEffect
import com.arjun.core.state_management.UiEvent
import com.arjun.core.state_management.UiState
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location

class CurrentLocationWeatherDetailScreenContract {

    sealed class Event : UiEvent {
        data object OnBackPress : Event()
        data class SaveToDb(val currentLocationWeather: CurrentLocationWeather?) : Event()
        data class DeleteFromDb(val latLon: String?) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        data object NavigateUp : Effect()

    }

    data class State(
        val isSaved: Boolean = false,
        val isLoading: Boolean = true,
        val currentLocationWeather: CurrentLocationWeather? = null,
        val cachedLocationList: List<Location> = emptyList()
    ) : UiState
}
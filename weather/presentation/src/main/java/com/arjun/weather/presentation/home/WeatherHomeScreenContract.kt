package com.arjun.weather.presentation.home

import com.arjun.core.state_management.UiEffect
import com.arjun.core.state_management.UiEvent
import com.arjun.core.state_management.UiState
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.model.Location

class WeatherHomeScreenContract {

    sealed class Event : UiEvent {
        data class OnQueryChange(val query: String) : Event()
        data object OnSearch : Event()

        data class OnItemDismissed(val item: CurrentLocationWeather) : Event()

        data object GetSavedLocations : Event()
        data object OnClearSearch : Event()
        data class OnSearchFocusChange(val isFocused: Boolean) : Event()
        data class OnSearchLocationResultClick(val slug: String) : Event()
        data object OnUseCurrentLocation : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        data class NavigateToCurrentLocationWeatherDetailScreen(val slug: String) : Effect()
    }

    data class State(
        val query: String? = null,
        val showHint: Boolean = true,
        val isSearching: Boolean = false,
        val searchLocationResult: List<Location> = emptyList(),
        val savedLocations: List<CurrentLocationWeather> = emptyList(),
        val currentLocation: android.location.Location? = null
    ) : UiState
}
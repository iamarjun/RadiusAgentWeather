package com.arjun.weather.presentation.home

import com.arjun.core.state_management.UiEffect
import com.arjun.core.state_management.UiEvent
import com.arjun.core.state_management.UiState
import com.arjun.weather.domain.model.Location
import java.time.LocalDate

class WeatherHomeScreenContract {

    sealed class Event : UiEvent {
        data class OnQueryChange(val query: String) : Event()
        data object OnSearch : Event()
        data class OnSearchFocusChange(val isFocused: Boolean) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        data object NavigateUp : Effect()
    }

    data class State(
        val query: String? = null,
        val showHint: Boolean = true,
        val isSearching: Boolean = false,
        val savedLocations: List<Location> = emptyList()
    ) : UiState
}
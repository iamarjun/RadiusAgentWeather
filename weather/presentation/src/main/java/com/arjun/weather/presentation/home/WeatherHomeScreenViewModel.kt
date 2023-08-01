package com.arjun.weather.presentation.home

import androidx.lifecycle.viewModelScope
import com.arjun.core.state_management.BaseViewModel
import com.arjun.weather.domain.location.LocationTracker
import com.arjun.weather.domain.usecases.SearchLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
    private val searchLocation: SearchLocation,
    private val locationTracker: LocationTracker
) : BaseViewModel<WeatherHomeScreenContract.Event, WeatherHomeScreenContract.State, WeatherHomeScreenContract.Effect>() {
    override fun createInitialState(): WeatherHomeScreenContract.State {
        return WeatherHomeScreenContract.State()
    }

    override fun handleEvent(event: WeatherHomeScreenContract.Event) {
        when (event) {
            is WeatherHomeScreenContract.Event.OnQueryChange -> {
                setState { copy(query = event.query) }
            }

            WeatherHomeScreenContract.Event.OnSearch -> {
                executeSearch()
            }

            is WeatherHomeScreenContract.Event.OnSearchFocusChange -> {
                setState {
                    copy(
                        showHint = !event.isFocused && currentState.query.isNullOrBlank()
                    )
                }
            }

            is WeatherHomeScreenContract.Event.OnSearchLocationResultClick -> {

            }
            WeatherHomeScreenContract.Event.OnUseCurrentLocation -> {

            }
            WeatherHomeScreenContract.Event.OnClearSearch -> {
                setState {
                    copy(
                        query = null,
                        searchLocationResult = emptyList(),
                        isSearching = false
                    )
                }
            }
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            val currentLocation = locationTracker.getCurrentLocation()
            println("Location: $currentLocation")
            setState {
                copy(
                    currentLocation = currentLocation
                )
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            setState {
                copy(
                    isSearching = true,
                )
            }

            currentState.query?.let { query ->
                searchLocation.invoke(query)
                    .onSuccess {
                        println("Location $it")
                        setState {
                            copy(
                                isSearching = false,
                                searchLocationResult = it
                            )
                        }
                    }
                    .onFailure {
                        setState {
                            copy(
                                isSearching = false,
                            )
                        }
                        setEffect {
                            WeatherHomeScreenContract.Effect.ShowToast(
                                it.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }

            }
        }
    }
}
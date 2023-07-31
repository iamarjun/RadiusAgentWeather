package com.arjun.weather.presentation.home

import androidx.lifecycle.viewModelScope
import com.arjun.core.state_management.BaseViewModel
import com.arjun.weather.domain.usecases.SearchLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
    private val searchLocation: SearchLocation
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
                                savedLocations = it
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
package com.arjun.weather.presentation.home

import com.arjun.core.state_management.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
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
//                executeSearch()
            }

            is WeatherHomeScreenContract.Event.OnSearchFocusChange -> {
                setState {
                    copy(
                        showHint = !event.isFocused && currentState.query.isNullOrBlank()
                    )
                }
            }

            else -> {}
        }
    }
}
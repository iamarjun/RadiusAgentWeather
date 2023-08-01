package com.arjun.radiusagentweather.navigation

import androidx.navigation.NavController
import com.arjun.weather.presentation.destinations.CurrentLocationWeatherDetailScreenDestination
import com.arjun.weather.presentation.detail.CurrentLocationWeatherDetailScreenNavigator
import com.arjun.weather.presentation.home.WeatherHomeScreenNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec

class CoreFeatureNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : WeatherHomeScreenNavigator, CurrentLocationWeatherDetailScreenNavigator {

    override fun navigateToWeatherDetailScreen(slug: String) {
        navController.navigate(CurrentLocationWeatherDetailScreenDestination(slug = slug))
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

}
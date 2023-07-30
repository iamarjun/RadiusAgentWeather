package com.arjun.radiusagentweather.navigation

import com.arjun.weather.presentation.WeatherNavGraph
import com.arjun.weather.presentation.destinations.WeatherHomeScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object RootNavGraph : NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = WeatherHomeScreenDestination

    override val nestedNavGraphs: List<NavGraphSpec> = listOf(WeatherNavGraph)

}
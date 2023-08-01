package com.arjun.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentLocationWeather(
    val location: Location,
    val current: Weather,
    val alerts: Alerts?,
    val forecast: Forecast?
) : Parcelable
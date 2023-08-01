package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Forecast(
    val forecastday: List<Forecastday?>?
) : Parcelable

package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep

import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Forecastday(
    val astro: Astro?,
    val date: String?,
    val day: Day?,
    val hour: List<Hour?>?
) : Parcelable
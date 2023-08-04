package com.arjun.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val country: String?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    val url: String?
) : Parcelable
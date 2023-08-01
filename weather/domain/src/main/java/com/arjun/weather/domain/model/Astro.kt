package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Astro(
    val isMoonUp: Int?,
    val isSunUp: Int?,
    val moonIllumination: String?,
    val moonPhase: String?,
    val moonrise: String?,
    val moonset: String?,
    val sunrise: String?,
    val sunset: String?
) : Parcelable

       
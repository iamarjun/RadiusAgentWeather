package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AstroResult(
    @Json(name = "is_moon_up")
    val isMoonUp: Int?,
    @Json(name = "is_sun_up")
    val isSunUp: Int?,
    @Json(name = "moon_illumination")
    val moonIllumination: String?,
    @Json(name = "moon_phase")
    val moonPhase: String?,
    @Json(name = "moonrise")
    val moonrise: String?,
    @Json(name = "moonset")
    val moonset: String?,
    @Json(name = "sunrise")
    val sunrise: String?,
    @Json(name = "sunset")
    val sunset: String?
) : Parcelable

       
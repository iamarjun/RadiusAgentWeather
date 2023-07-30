package com.arjun.weather.data.remote.dto


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LocationResult(
    @Json(name = "country")
    val country: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "url")
    val url: String?
) : Parcelable
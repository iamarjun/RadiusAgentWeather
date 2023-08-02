package com.arjun.weather.data.remote.dto


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AlertResult(
    @Json(name = "areas")
    val areas: String?,
    @Json(name = "category")
    val category: String?,
    @Json(name = "certainty")
    val certainty: String?,
    @Json(name = "desc")
    val desc: String?,
    @Json(name = "effective")
    val effective: String?,
    @Json(name = "event")
    val event: String?,
    @Json(name = "expires")
    val expires: String?,
    @Json(name = "headline")
    val headline: String?,
    @Json(name = "instruction")
    val instruction: String?,
    @Json(name = "msgtype")
    val msgtype: String?,
    @Json(name = "note")
    val note: String?,
    @Json(name = "severity")
    val severity: String?,
    @Json(name = "urgency")
    val urgency: String?
) : Parcelable
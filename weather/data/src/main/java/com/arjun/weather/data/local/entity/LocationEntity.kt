package com.arjun.weather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    val country: String?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    val url: String?,
    val icon: String?,
    val text: String?,
    val feelslikeC: Double?,
    val precipMm: Double?,
    val uv: Double?,
    val windDir: String?,
    val windKph: Double?,
) {
    @PrimaryKey
    var latLon = "$lat,$lon"
}
package com.arjun.weather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)

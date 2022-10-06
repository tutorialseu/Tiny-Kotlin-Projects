package eu.tutorials.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Wind(
    val speed: Double,
    @SerializedName("deg")
    val degree: Int
)
package eu.tutorials.weatherapp.models

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
package eu.tutorials.tour.utils

sealed class Resource<out T> {
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Success<out T>(val data: T? = null, val message: String = "") : Resource<T>()
    data class Failure<out T>(val message: String) : Resource<T>()
}
package eu.tutorials.weatherapp.network


import eu.tutorials.weatherapp.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * An Interface which defines the HTTP operations Functions.
 */
interface WeatherService {

    @GET("2.5/weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String?
    ): Call<WeatherResponse>
}
// END


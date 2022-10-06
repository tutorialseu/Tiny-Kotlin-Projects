package eu.tutorials.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonParser
import eu.tutorials.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

getWeatherForecast()

        }


    fun getWeatherForecast() {
        Executors.newSingleThreadExecutor().execute {
            var httpURLConnection:HttpURLConnection? = null
            try {
                val url =
                    URL("https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=da15f28cbf49f1f8a2f030949b85e9a1")
                 httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                ) // The format of response we want to get from the server
                httpURLConnection.requestMethod = "GET"
                httpURLConnection.doInput = true
                httpURLConnection.doOutput = false
                // Check if the connection is successful
                val responseCode = httpURLConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = httpURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }  // defaults to UTF-8

                    // Convert raw JSON to pretty JSON using GSON library
                    val json = JsonParser().parse(response).toString()
                    val jObject = JSONObject(json)
                    val forecast = jObject.getJSONArray("weather")
                    val desc = forecast.getJSONObject(0).getString("description")
                    //Toast.makeText(this, desc, Toast.LENGTH_LONG).show()

                    Log.d("weather response ::", json)
                } else {
                    Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
                }
            }catch (e:Exception){
                Log.e("error",e.stackTraceToString())
            } finally {
                httpURLConnection?.disconnect()
            }
        }
    }
}
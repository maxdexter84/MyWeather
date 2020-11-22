package ru.maxdexter.myweather.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.maxdexter.myweather.model.WeatherData
import java.util.concurrent.Flow

interface Api {
    //https://api.openweathermap.org/data/2.5/onecall?lat=55.00&lon=50.00&appid=073f40e104f2129961514beb51a721d2

    @GET(value = "data/2.5/onecall")
    fun getWeatherAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String = "073f40e104f2129961514beb51a721d2",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ):Deferred<WeatherData>


}
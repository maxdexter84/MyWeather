package ru.maxdexter.myweather.repository

import ru.maxdexter.myweather.network.ApiService

object Repository {



    fun getDataFromApi(lat: String, lon: String )  =
        ApiService.api.getWeatherAsync(lat,lon)
}
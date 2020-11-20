package ru.maxdexter.myweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.repository.Repository

class MainViewModel : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(job + Dispatchers.IO)


    private val _weatherData = MutableLiveData<LoadData<WeatherData>>()
    val weatherData: LiveData<LoadData<WeatherData>>
        get() = _weatherData

    fun loadCurrentWeather(lat: String, lon: String) {
        uiScope.launch {
            val result = Repository.getDataFromApi(lat,lon)
            _weatherData.postValue(handleWeatherData(result))
        }
    }

    private suspend fun handleWeatherData(result: Deferred<WeatherData>): LoadData<WeatherData>? {
        return try {
            _weatherData.value = LoadData.Loading()
            LoadData.Success(result.await())
        }catch (e: Exception) {
            LoadData.Error(e.message)
        }

    }
}
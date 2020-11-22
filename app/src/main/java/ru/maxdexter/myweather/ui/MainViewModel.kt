package ru.maxdexter.myweather.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.repository.Repository

class MainViewModel : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private var _coordinates = MutableLiveData<Pair<Double, Double>?>(null)
    val coordinates: LiveData<Pair<Double, Double>?>
        get() = _coordinates

    private val _weatherData = MutableLiveData<LoadData<WeatherData>>()
    val weatherData: LiveData<LoadData<WeatherData>>
        get() = _weatherData

    init {
        loadCurrentWeather("55.00", "50.00")
        _weatherData.value = LoadData.Loading()
    }

    fun loadCurrentWeather(lat: String, lon: String) {
        uiScope.launch {
            val result = Repository.getDataFromApi(lat,lon)
            _weatherData.value = (handleWeatherData(result))
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

    fun requestLocation(context: Context) {
        val geocoder = Geocoder(context)
        // Получаем менеджер геолокаций
        val locationManager = ContextCompat.getSystemService(context, LocationManager::class.java)
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
        val provider = locationManager?.getBestProvider(criteria, true)

        if (provider != null && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {

            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10f, object : LocationListener {

                override fun onLocationChanged(location: Location){
                    val lat = location.latitude // Широта
                   val lon = location.longitude // Долгота
                    loadCurrentWeather(lat.toString(), lon.toString())
                    _coordinates.value = lat to lon
//                    val accuracy = java.lang.Float.toString(location.accuracy) // Точность
//                    val currentPosition = LatLng(lat, lon)

                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            })

        }

    }
}
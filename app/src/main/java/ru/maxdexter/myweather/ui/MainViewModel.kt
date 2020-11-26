package ru.maxdexter.myweather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.text.Editable
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.maxdexter.myweather.LoadData
import ru.maxdexter.myweather.model.WeatherData
import ru.maxdexter.myweather.model.roommodel.SearchHistory
import ru.maxdexter.myweather.repository.Repository
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private var _placeName = MutableLiveData<String>("")
    val placeName: LiveData<String>
        get() = _placeName

    private val _weatherData = MutableLiveData<LoadData<WeatherData>>()
    val weatherData: LiveData<LoadData<WeatherData>>
        get() = _weatherData

    private val _currentCity = MutableLiveData<MutableList<String>>()
            val currentCity: LiveData<MutableList<String>>
            get() = _currentCity

    init {

        _weatherData.value = LoadData.Loading()
    }

     fun loadCurrentWeather(latLon: Pair<String, String>) {
        viewModelScope.launch {
            val result = Repository.getDataFromApi(latLon.first,latLon.second)
            _weatherData.value = (handleWeatherData(result))
        }

    }

    private  fun handleWeatherData(result: WeatherData): LoadData<WeatherData>? {
        return try {
            _weatherData.value = LoadData.Loading()
            LoadData.Success(result)
        }catch (e: Exception) {
            LoadData.Error(e.message)
        }

    }

    @SuppressLint("WrongConstant")
    fun requestLocation(context: Context, locationManager: LocationManager) {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_LOW
        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null)
        locationManager.requestLocationUpdates(provider, 10000, 10f
        ) { location ->
            val lat = location.latitude // Широта
            val lon = location.longitude // Долгота
            loadCurrentWeather(lat.toString() to lon.toString())
            viewModelScope.launch() {
                _placeName.value = getAddress(lat, lon,context)
            }

        }

    }



     fun searchPlace(s: Editable, context: Context){
         viewModelScope.launch(Dispatchers.IO) {
             if (s.length > 2) {
                 delay(1000)
                 val latLon = getCoordinates(s.toString(), context)
                loadCurrentWeather(latLon.first.toString() to latLon.second.toString())
             }

         }

    }
    //Получаем адрес по координатам
    suspend fun getAddress(lon: Double, lat: Double, context: Context): String =
        // Поскольку Geocoder работает по интернету, создаём отдельный поток
        withContext(Dispatchers.IO){
            suspendCoroutine {continuation->
                try {
                    val geocoder = Geocoder(context)
                    val addresses = geocoder.getFromLocation(lon, lat, 1)
                    if (addresses.size > 0){

                        val addr = addresses[0].getAddressLine(0).split(",".toRegex()).toTypedArray()
                        val city = addr[2]
                        val search = SearchHistory(name = city,lat = lat.toString(),lon = lon.toString())
                        saveSearch(search, context)
                        continuation.resume(city)
                    }

                } catch (e: IOException) {
                    continuation.resumeWithException(e)
                }
            }

        }

    //Получаем координаты по адресу
    suspend fun getCoordinates(name: String, context: Context): Pair<Double, Double> =
        suspendCoroutine {continuation ->
            // Поскольку Geocoder работает по интернету, создаём отдельный поток
            try {
                val geocoder = Geocoder(context)
                val coords = geocoder.getFromLocationName(name, 1)
                if (coords.size > 0){
                    _placeName.value = coords[0].locality.split(",")[0]
                    val lat = coords[0].latitude
                    val lon = coords[0].longitude
                    val searchHistory = SearchHistory(name = name, lat = lat.toString(), lon = lon.toString())
                    saveSearch(searchHistory, context)
                    continuation.resume(lat to lon)
                }
            } catch (e: IOException) {
                continuation.resumeWithException(e)
            }
        }

    private  fun saveSearch(searchHistory: SearchHistory, context: Context){
        uiScope.launch {
            Repository.saveHistory(searchHistory,context)
        }
    }

     fun getAllHistory(context: Context):LiveData<List<SearchHistory>> {
        return Repository.getAllHistory(context)
    }
}
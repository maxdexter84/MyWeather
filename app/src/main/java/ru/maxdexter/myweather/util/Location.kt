package ru.maxdexter.myweather.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.location.Location
import android.os.Bundle
import android.os.CancellationSignal
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Runnable
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Location (private val activity: Activity){
    private val geocoder = Geocoder(activity.applicationContext)

    /*-------------------------------------------------------------*/



    //Получаем адрес по координатам
   suspend fun getAddress(lon: Double, lat: Double): String =
        // Поскольку Geocoder работает по интернету, создаём отдельный поток
        suspendCoroutine {continuation->
            try {
                val addresses = geocoder.getFromLocation(lon, lat, 1)
                val addr = addresses[0].getAddressLine(0).split(",".toRegex()).toTypedArray()
                val city = addr[2]
                continuation.resume(city)
            } catch (e: IOException) {
                continuation.resumeWithException(e)
            }
        }




    //Получаем координаты по адресу
   suspend fun getCoordinates(name: String): Pair<String, String> =
        suspendCoroutine {continuation ->
            // Поскольку Geocoder работает по интернету, создаём отдельный поток
            try {
                val coords = geocoder.getFromLocationName(name, 1)
                val lat = coords[0].latitude.toString()
                val lon = coords[0].longitude.toString()
                continuation.resume(lat to lon)

            } catch (e: IOException) {
               continuation.resumeWithException(e)
            }
        }


}
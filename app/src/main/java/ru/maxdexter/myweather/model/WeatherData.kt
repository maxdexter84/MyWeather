package ru.maxdexter.myweather.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize
data class WeatherData(
    @SerializedName("alerts")
    val alerts: List<Alert>,
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lon")
    val lon: Int,
    @SerializedName("minutely")
    val minutely: List<Minutely>,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
) : Parcelable
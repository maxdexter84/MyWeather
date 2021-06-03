package ru.maxdexter.myweather.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SnowX(
    @SerializedName("1h")
    val h: Double
) : Parcelable
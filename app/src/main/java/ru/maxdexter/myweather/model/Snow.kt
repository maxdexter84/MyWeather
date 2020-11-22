package ru.maxdexter.myweather.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Snow(
    @SerializedName("1h")
    val h: Double
) : Parcelable
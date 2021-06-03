package ru.maxdexter.myweather.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Alert(
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: Int,
    @SerializedName("event")
    val event: String,
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("start")
    val start: Int
) : Parcelable
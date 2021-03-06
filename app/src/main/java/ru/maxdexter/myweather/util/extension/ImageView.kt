package ru.maxdexter.myweather.util.extension

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

fun bindImage(imageView: ImageView,imgId: String?) {
    val url = "http://openweathermap.org/img/wn/${imgId}@2x.png"
    Log.i("IMAGE", url)
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
}
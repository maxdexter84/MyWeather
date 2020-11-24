package ru.maxdexter.myweather.extension

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.maxdexter.myweather.R

fun bindImage(imageView: ImageView,imgId: String?) {
    val url = "http://openweathermap.org/img/wn/${imgId}@2x.png"
    Log.i("IMAGE", url)
        Glide.with(imageView.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_brocken_img))
            .into(imageView)
}
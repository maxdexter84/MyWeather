package ru.maxdexter.myweather.extension

import java.text.SimpleDateFormat
import java.util.*
const val DATE_TIME_FORMAT = "dd.MMM"
fun Date.currentDate(date: Long?): String{
    var currentDate = ""
    if (date != null)
    currentDate  = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date * 1000)
    return currentDate
}
package ru.maxdexter.myweather

sealed class LoadData<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): LoadData<T>(data)
    class Error<T>(message: String?, data: T? = null) : LoadData<T>(data,message)
    class Loading<T> : LoadData<T>()
}
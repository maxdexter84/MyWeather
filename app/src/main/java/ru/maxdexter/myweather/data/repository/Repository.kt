package ru.maxdexter.myweather.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.maxdexter.myweather.data.database.HistoryData
import ru.maxdexter.myweather.data.model.roommodel.SearchHistory
import ru.maxdexter.myweather.data.network.ApiService

object Repository {

    suspend fun getDataFromApi(lat: String, lon: String )  =
        withContext(Dispatchers.IO){
            ApiService.api.getWeatherAsync(lat,lon).await()
        }



    suspend fun saveHistory(searchHistory: SearchHistory, context: Context) {
        HistoryData.invoke(context).historyDao().insert(searchHistory)
    }

    suspend fun deleteHistory(searchHistory: SearchHistory, context: Context){
        HistoryData.invoke(context).historyDao().delete(searchHistory)
    }

    fun getAllHistory(context: Context): LiveData<List<SearchHistory>>{
        return HistoryData.invoke(context).historyDao().getAllHistory()
    }

}
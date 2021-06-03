package ru.maxdexter.myweather.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.maxdexter.myweather.data.model.roommodel.SearchHistory

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)

    @Query("SELECT * FROM SearchHistory")
    fun getAllHistory(): LiveData<List<SearchHistory>>


}
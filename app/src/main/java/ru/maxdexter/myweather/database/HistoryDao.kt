package ru.maxdexter.myweather.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.maxdexter.myweather.model.roommodel.SearchHistory
import java.util.concurrent.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)

    @Query("SELECT * FROM SearchHistory")
    fun getAllHistory(): LiveData<SearchHistory>


}
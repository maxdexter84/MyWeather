package ru.maxdexter.myweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryData::class], version = 1)
abstract class HistoryData: RoomDatabase() {
    abstract fun historyDao(): HistoryDao


    companion object {
        //Singleton
        @Volatile
        private var instance: HistoryData? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context): HistoryData {
            return Room.databaseBuilder(context.applicationContext, HistoryData::class.java, "database.db").build()
        }


    }
}
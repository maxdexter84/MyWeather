package ru.maxdexter.myweather.model.roommodel

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class SearchHistory(@PrimaryKey(autoGenerate = true)
                         val id: Int,
                         val name: String = "",
                         val lat: String = "",
                         val lon: String = "")

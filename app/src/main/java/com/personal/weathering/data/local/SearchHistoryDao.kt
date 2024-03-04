package com.personal.weathering.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToHistory(searchResult: SearchResultEntity)

    @Delete
    suspend fun removeFromHistory(searchResult: SearchResultEntity)

    @Query("SELECT * FROM searchresultentity")
    fun getSearchHistory(): Flow<List<SearchResultEntity>>
}
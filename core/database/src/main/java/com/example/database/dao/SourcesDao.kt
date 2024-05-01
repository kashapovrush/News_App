package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.dbModel.SourceDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SourcesDao {

    @Query("SELECT * FROM sources")
    fun getCachedSources(): Flow<List<SourceDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCachedSources(list: List<SourceDb>)

    @Delete
    suspend fun removeCachedSources(list: List<SourceDb>)

    @Query("DELETE FROM sources")
    suspend fun clearCached()
}
package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.dbModel.NewsHeadlinesDb
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsHeadlinesDao {

    @Query("SELECT * FROM newsposts" )
    fun getList(): Flow<List<NewsHeadlinesDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewsPost(post: NewsHeadlinesDb)

    @Query("DELETE FROM newsposts WHERE title=:title")
    suspend fun deleteNewsPost(title: String)

    @Query("SELECT * FROM newsposts" )
    fun getCaches(): List<NewsHeadlinesDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCache (post: NewsHeadlinesDb): Completable

    @Query("DELETE FROM newsposts")
    fun clearList(): Completable


}
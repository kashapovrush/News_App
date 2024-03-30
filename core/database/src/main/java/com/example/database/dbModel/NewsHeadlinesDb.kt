package com.example.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsposts")
data class NewsHeadlinesDb(
    val id: Int = 0,
    val source: String,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String?,
    val content: String?,
    val isFavourite: Int = 0,
    val deleteTime: Long
)

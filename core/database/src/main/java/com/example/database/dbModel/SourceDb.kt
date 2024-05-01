package com.example.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class SourceDb(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var name: String,
    var category: String,
    var country: String
)

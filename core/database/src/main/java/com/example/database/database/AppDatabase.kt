package com.example.database.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.dao.SourcesDao
import com.example.database.dbModel.NewsHeadlinesDb
import com.example.database.dbModel.SourceDb


@Database(entities = [NewsHeadlinesDb::class, SourceDb::class], version = 3, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun newsHeadlinesDao(): NewsHeadlinesDao

    abstract fun sourcesDao(): SourcesDao

    companion object {

        private var db: AppDatabase? = null
        const val DB_NAME = "newsposts"
        private val LOCK = Any()

//        val MIGRATION1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE newsposts ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
//                db.execSQL("ALTER TABLE newsposts ADD COLUMN deleteTime INTEGER NOT NULL")
//            }
//
//        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS sources (id TEXT NOT NULL, name TEXT NOT NULL, category TEXT NOT NULL, country TEXT NOT NULL, PRIMARY KEY(`id`))"
                )
            }

        }

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = DB_NAME
                ).addMigrations(MIGRATION_2_3)
                    .build()
                db = instance
                return instance
            }
        }
    }
}
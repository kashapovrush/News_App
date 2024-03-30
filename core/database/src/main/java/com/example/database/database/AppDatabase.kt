package com.example.database.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.dbModel.NewsHeadlinesDb

@Database(entities = [NewsHeadlinesDb::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsHeadlinesDao(): NewsHeadlinesDao

    companion object {

        private var db: AppDatabase? = null
        const val DB_NAME = "newsposts"
        private val LOCK = Any()

        val MIGRATION1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE newsposts ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE newsposts ADD COLUMN deleteTime INTEGER NOT NULL")
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
                ).addMigrations(MIGRATION1_2)
                    .build()
                db = instance
                return instance
            }
        }
    }
}
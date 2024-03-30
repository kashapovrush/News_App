package com.example.features_news_post.di

import android.app.Application
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.database.AppDatabase
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
interface NewsPostModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideNewsDatabase(application: Application): NewsHeadlinesDao {
            return AppDatabase.getInstance(application).newsHeadlinesDao()
        }
    }
}
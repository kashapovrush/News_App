package com.example.features_common.di

import android.app.Application
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.database.AppDatabase
import com.kashapovrush.api.network.ApiFactory
import com.kashapovrush.api.network.ApiService
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
interface CommonModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideNewsHeadlinesDao(application: Application): NewsHeadlinesDao {
            return AppDatabase.getInstance(application).newsHeadlinesDao()
        }

//        @ApplicationScope
//        @Provides
//        fun providePageSource()
    }
}
package com.example.features_source.di

import android.app.Application
import android.content.Context
import com.example.database.dao.SourcesDao
import com.example.database.database.AppDatabase
import com.example.prefrences.PreferencesManager
import com.example.prefrences.PreferencesManagerImpl
import com.kashapovrush.api.network.ApiFactory
import com.kashapovrush.api.network.ApiService
import dagger.Module
import dagger.Provides

@Module
object SourceModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

    @Provides
    fun provideSourcesDao(application: Application): SourcesDao {
        return AppDatabase.getInstance(application).sourcesDao()
    }

    @Provides
    fun providePreferences(context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }
}
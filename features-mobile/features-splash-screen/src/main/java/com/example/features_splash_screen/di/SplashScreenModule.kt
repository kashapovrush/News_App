package com.example.features_splash_screen.di

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
object SplashScreenModule {

    @Provides
    fun providePreferences(context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }

    @Provides
    fun provideSourceDao(context: Context): SourcesDao {
        return AppDatabase.getInstance(context).sourcesDao()
    }

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

}
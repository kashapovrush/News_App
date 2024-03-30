package com.example.features_filter_news.di

import android.content.Context
import com.example.prefrences.PreferencesManager
import com.example.prefrences.PreferencesManagerImpl
import com.kashapovrush.api.network.ApiFactory
import com.kashapovrush.api.network.ApiService
import dagger.Module
import dagger.Provides

@Module
interface FilterNewsModule {

    companion object {

        @Provides
        fun provideSharedPreferences(context: Context): PreferencesManager {
            return PreferencesManagerImpl(context)
        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }


}
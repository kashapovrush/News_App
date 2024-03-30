package com.example.features_source.di

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
}
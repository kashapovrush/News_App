package com.example.features_search.di

import com.kashapovrush.api.network.ApiFactory
import com.kashapovrush.api.network.ApiService
import dagger.Module
import dagger.Provides

@Module
interface SearchHeadlinesModule {


    companion object {

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}
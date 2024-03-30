package com.kashapovrush.headlines_features.di;

import android.app.Application;

import com.example.database.dao.NewsHeadlinesDao;
import com.example.database.database.AppDatabase;
import com.kashapovrush.api.network.ApiFactory;
import com.kashapovrush.api.network.ApiService;
import com.kashapovrush.utils.ApplicationScope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
interface HeadlinesModule {
    @ApplicationScope
    @Provides
    static ApiService provideApiService() {
        return ApiFactory.INSTANCE.getApiService();
    }

    @ApplicationScope
    @Provides
    static NewsHeadlinesDao provideNewsHeadlinesDao (Application application)  {
        return AppDatabase.Companion.getInstance(application).newsHeadlinesDao();
    }

}
package com.example.features_search.di

import android.content.Context
import com.example.features_search.ui.SearchHeadlinesFragment
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.Provides

@ApplicationScope
@Component(modules = [SearchHeadlinesModule::class, ViewModelModule::class])
interface SearchHeadlinesComponent {

    fun inject(fragment: SearchHeadlinesFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): SearchHeadlinesComponent
    }
}
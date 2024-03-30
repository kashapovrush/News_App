package com.example.features_filter_news.di

import android.content.Context
import com.example.features_filter_news.ui.ChooseFilterFragment
import com.example.features_filter_news.ui.FilterNewsFragment
import com.example.features_filter_news.ui.FilteredNewsFragment
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [FilterNewsModule::class, ViewModelModule::class])
interface FilterNewsComponent {

    fun inject(fragment: ChooseFilterFragment)

    fun inject(fragment: FilterNewsFragment)

    fun inject(fragment: FilteredNewsFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): FilterNewsComponent
    }
}
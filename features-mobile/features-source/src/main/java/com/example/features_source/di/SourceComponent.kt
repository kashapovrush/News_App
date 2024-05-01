package com.example.features_source.di

import android.app.Application
import android.content.Context
import com.example.features_source.ui.SourceNewsFragment
import com.example.features_source.ui.SourcesFragment
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, SourceModule::class])
interface SourceComponent {

    fun inject(fragment: SourcesFragment)

    fun inject(fragment: SourceNewsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application, @BindsInstance context: Context): SourceComponent
    }
}
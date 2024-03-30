package com.example.features_favourite.di

import android.app.Application
import android.content.Context
import com.example.features_favourite.ui.FavouriteFragment
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, FavouriteModule::class])
interface FavouriteComponent {

    fun inject(fragment: FavouriteFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): FavouriteComponent
    }
}
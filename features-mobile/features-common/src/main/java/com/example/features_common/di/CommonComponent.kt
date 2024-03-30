package com.example.features_common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CommonModule::class, ViewModelModule::class])
interface CommonComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CommonComponent
    }
}
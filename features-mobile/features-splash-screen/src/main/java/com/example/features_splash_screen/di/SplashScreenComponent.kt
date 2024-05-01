package com.example.features_splash_screen.di

import android.app.Application
import android.content.Context
import com.example.features_splash_screen.ui.SplashScreenFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [SplashScreenModule::class, ViewModelModule::class])
interface SplashScreenComponent {

    fun inject(fragment: SplashScreenFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context
        ): SplashScreenComponent
    }
}
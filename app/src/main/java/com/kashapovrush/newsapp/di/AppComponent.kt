package com.kashapovrush.newsapp.di

import android.content.Context
import com.kashapovrush.newsapp.MainActivity
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create (@BindsInstance context: Context): AppComponent
    }
}
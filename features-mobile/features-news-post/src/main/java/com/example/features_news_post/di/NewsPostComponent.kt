package com.example.features_news_post.di

import android.app.Application
import com.example.features_news_post.ui.NewsPostFragment
import com.kashapovrush.utils.ApplicationScope.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [NewsPostModule::class, ViewModelModule::class])
interface NewsPostComponent {

    fun inject(fragment: NewsPostFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): NewsPostComponent
    }

}
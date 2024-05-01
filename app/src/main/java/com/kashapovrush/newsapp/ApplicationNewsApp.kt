package com.kashapovrush.newsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.features_common.di.CommonComponent
import com.example.features_common.di.CommonComponentProvider
import com.example.features_common.di.DaggerCommonComponent
import com.example.features_favourite.di.DaggerFavouriteComponent
import com.example.features_favourite.di.FavouriteComponent
import com.example.features_favourite.di.FavouriteComponentProvider
import com.example.features_filter_news.di.DaggerFilterNewsComponent
import com.example.features_filter_news.di.FilterNewsComponent
import com.example.features_filter_news.di.FilterNewsComponentProvider
import com.example.features_news_post.di.DaggerNewsPostComponent
import com.example.features_news_post.di.NewsPostComponent
import com.example.features_news_post.di.NewsPostComponentProvider
import com.example.features_search.di.DaggerSearchHeadlinesComponent
import com.example.features_search.di.SearchHeadlinesComponent
import com.example.features_search.di.SearchHeadlinesComponentProvider
import com.example.features_source.di.DaggerSourceComponent
import com.example.features_source.di.SourceComponent
import com.example.features_source.di.SourceComponentProvider
import com.example.features_splash_screen.di.DaggerSplashScreenComponent
import com.example.features_splash_screen.di.SplashScreenComponent
import com.example.features_splash_screen.di.SplashScreenComponentProvider
import com.kashapovrush.headlines_features.di.DaggerHeadlinesComponent
import com.kashapovrush.headlines_features.di.HeadlinesComponent
import com.kashapovrush.headlines_features.di.HeadlinesComponentProvider

class ApplicationNewsApp : Application(), HeadlinesComponentProvider, CommonComponentProvider,
    SearchHeadlinesComponentProvider, FilterNewsComponentProvider, NewsPostComponentProvider,
    FavouriteComponentProvider, SourceComponentProvider, SplashScreenComponentProvider {

    override fun getHeadlinesComponent(): HeadlinesComponent {
        return DaggerHeadlinesComponent.factory().create(this, this)
    }

    override fun getCommonComponent(): CommonComponent {
        return DaggerCommonComponent.factory().create(this)
    }

    override fun getSearchHeadlinesComponent(): SearchHeadlinesComponent {
        return DaggerSearchHeadlinesComponent.factory().create(this)
    }

    override fun getFilterNewsComponentProvider(): FilterNewsComponent {
        return DaggerFilterNewsComponent.factory().create(this)
    }

    override fun getNewsPostComponent(): NewsPostComponent {
        return DaggerNewsPostComponent.factory().create(this)
    }

    override fun getFavouriteComponent(): FavouriteComponent {
        return DaggerFavouriteComponent.factory().create(this)
    }

    override fun getSourceComponent(): SourceComponent {
        return DaggerSourceComponent.factory().create(this, this)
    }

    override fun getSplashScreenComponent(): SplashScreenComponent {
        return DaggerSplashScreenComponent.factory().create(this, this)
    }


//    fun isNetworkAvailable(context: Context): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork = connectivityManager.activeNetworkInfo
//        return activeNetwork != null && activeNetwork.isConnected
//    }

}
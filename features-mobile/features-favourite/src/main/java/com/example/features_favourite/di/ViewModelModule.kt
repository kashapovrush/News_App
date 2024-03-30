package com.example.features_favourite.di

import androidx.lifecycle.ViewModel
import com.example.features_common.di.ViewModuleKey
import com.example.features_common.viewmodel.FavouriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModuleKey(FavouriteViewModel::class)
    fun bindDatabaseViewModel(viewModel: FavouriteViewModel): ViewModel
}
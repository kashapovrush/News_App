package com.example.features_splash_screen.di

import androidx.lifecycle.ViewModel
import com.example.features_common.di.ViewModuleKey
import com.example.features_common.viewmodel.SourcesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModuleKey(SourcesViewModel::class)
    fun bindSourcesViewModel(viewModel: SourcesViewModel): ViewModel
}
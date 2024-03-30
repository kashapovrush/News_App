package com.example.features_search.di

import androidx.lifecycle.ViewModel
import com.example.features_common.di.ViewModuleKey
import com.example.features_common.viewmodel.CommonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModuleKey(CommonViewModel::class)
    fun bindViewModel(viewModel: CommonViewModel): ViewModel
}
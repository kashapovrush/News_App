package com.example.features_source.di

import androidx.lifecycle.ViewModel
import com.example.features_common.di.ViewModuleKey
import com.example.features_common.viewmodel.CommonViewModel
import com.example.features_common.viewmodel.SourcesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModuleKey(SourcesViewModel::class)
    fun bindSourceViewModel(viewModel: SourcesViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModuleKey(CommonViewModel::class)
    fun bindCommonViewModel(viewModel: CommonViewModel): ViewModel

}
package com.example.features_common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kashapovrush.api.modelsDto.Source
import com.kashapovrush.api.network.ApiService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SourcesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _sources = MutableLiveData<List<Source>>()
    val sources: LiveData<List<Source>> = _sources

    private val _loadingHeadlines = MutableLiveData<Boolean>()
    val loadingHeadlines: LiveData<Boolean> = _loadingHeadlines


    suspend fun getSource() = flow {
        _loadingHeadlines.postValue(true)
        emit(apiService.getSources("6e56239f10864bf189382ae97b6093dd"))
    }.catch {
        _loadingHeadlines.postValue(false)
    }.collect {
        _loadingHeadlines.postValue(false)
        _sources.postValue(it.sources)
    }

}
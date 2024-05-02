package com.example.features_common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.dao.SourcesDao
import com.example.features_common.mapper.NewsHeadlinesMapper
import com.example.features_common.models.Source
import com.kashapovrush.api.network.ApiService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SourcesViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sourcesDao: SourcesDao,
    private val mapper: NewsHeadlinesMapper
) : ViewModel() {

    private val _sources = MutableLiveData<List<Source>>()
    val sources: LiveData<List<Source>> = _sources

    private val _loadingHeadlines = MutableLiveData<Boolean>()
    val loadingHeadlines: LiveData<Boolean> = _loadingHeadlines


    val cachedSources = sourcesDao.getCachedSources().map { sourcesDb ->
        mapper.mapSourcesDbToSourcesEntities(sourcesDb)
    }.onStart {
        _loadingHeadlines.postValue(true)
    }.onEach {
        _loadingHeadlines.postValue(false)
        _sources.postValue(it)
    }.filter {
        it.isEmpty()
    }.onEach {
        _loadingHeadlines.postValue(true)
        val responseSources = apiService.getSources("6e56239f10864bf189382ae97b6093dd")
        sourcesDao.addCachedSources(mapper.mapSourcesListDtoToSourcesListDb(responseSources.sources))
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    suspend fun clearCached() {
        sourcesDao.clearCached()
    }

}
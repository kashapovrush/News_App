package com.example.features_common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.features_common.state.ViewState
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto
import com.kashapovrush.api.modelsDto.toNewsHeadlinesListEntity
import com.kashapovrush.api.network.ApiService
import com.kashapovrush.api.network.FilteredNewsPageSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommonViewModel @Inject constructor(
    private val apiService: ApiService,
    private val pageSource: FilteredNewsPageSource.Factory
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _headlinesList = MutableLiveData<List<NewsHeadlines>>()
    val headlinesList: LiveData<List<NewsHeadlines>> = _headlinesList

    private val _loadingHeadlines = MutableLiveData<Boolean>()
    val loadingHeadlines: LiveData<Boolean> = _loadingHeadlines

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun searchHeadlines(query: String) {
        _loadingHeadlines.value = true
        val disposable = apiService.searchHeadlines(query, "5e9ef018060345fc864ba15895a7956f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loadingHeadlines.value = false
                _headlinesList.value = it.articles.toNewsHeadlinesListEntity()
            }, {
                _loadingHeadlines.value = false
                _showError.value = it.message
            })
        disposables.add(disposable)
    }


//    suspend fun filteredNews(language: String, from: String, to: String, sortBy: String) = flow {
//        _viewState.postValue(ViewState.Loading)
//        emit(
//            apiService.filteredNews(
//                query = "news",
//                language = language,
//                from = from,
//                to = to,
//                sortBy = sortBy,
//                apiKey = "6e56239f10864bf189382ae97b6093dd"
//            )
//        )
//    }.catch {
//        _viewState.postValue(
//            it.message?.let { error ->
//                ViewState.Error(error)
//            }
//        )
//        _showError.value = it.message
//    }.collect {
//        viewModelScope.launch {
//            _viewState.value = ViewState.Success(it.articles)
//        }
//    }

    fun filteredNews(
        language: String,
        from: String,
        to: String,
        sortBy: String
    ) =
        Pager(config = PagingConfig(5)) {
            pageSource.create(
                query = "news",
                language = language,
                from = from,
                to = to,
                sortBy = sortBy,
                apiKey = "6e56239f10864bf189382ae97b6093dd"
            )
        }.flow.catch {
            _showError.postValue(it.message)
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

//    fun requestIntent(
//        intent: ViewIntent,
//        language: String,
//        from: String,
//        to: String,
//        sortBy: String
//    ) {
//        when (intent) {
//            is ViewIntent.RequestData -> {
//                viewModelScope.launch {
//                    filteredNews(language, from, to, sortBy)
//                }
//            }
//
//            is ViewIntent.UpdateList -> {
//                viewModelScope.launch {
//                    filteredNews(language, from, to, sortBy)
//                }
//            }
//        }
//    }

    suspend fun getSourceNews(sources: String) = flow {
        _loadingHeadlines.postValue(true)
        emit(apiService.getSourceNews(sources, "5e9ef018060345fc864ba15895a7956f"))
    }.catch {
        _loadingHeadlines.postValue(false)
        _showError.postValue(it.message)
    }.collect {
        viewModelScope.launch {
            _loadingHeadlines.value = false
            _headlinesList.value = it.articles.toNewsHeadlinesListEntity()
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
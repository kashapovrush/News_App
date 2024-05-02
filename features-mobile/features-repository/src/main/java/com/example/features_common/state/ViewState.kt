package com.example.features_common.state

import com.kashapovrush.api.modelsDto.NewsHeadlinesDto

sealed class ViewState() {

    object Loading: ViewState()

    data class Success(val result: List<NewsHeadlinesDto>): ViewState()

    data class Error(val error: String): ViewState()
}

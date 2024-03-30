package com.example.features_common.state

import com.kashapovrush.api.modelsDto.NewsHeadlines

sealed class ViewState() {

    object Loading: ViewState()

    data class Success(val result: List<NewsHeadlines>): ViewState()

    data class Error(val error: String): ViewState()
}

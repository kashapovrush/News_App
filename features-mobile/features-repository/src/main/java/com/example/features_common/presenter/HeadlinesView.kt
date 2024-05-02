package com.example.features_common.presenter

import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface HeadlinesView: MvpView {

    fun loading()

    fun showResult(result: List<NewsHeadlines>)

    fun updateHeadlines(result: List<NewsHeadlines>)

    fun showError(error: String)
}
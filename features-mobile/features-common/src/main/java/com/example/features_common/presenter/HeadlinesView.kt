package com.example.features_common.presenter

import com.example.database.dbModel.NewsHeadlinesDb
import com.kashapovrush.api.modelsDto.NewsHeadlines
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface HeadlinesView: MvpView {

    fun loading()

    fun showResult(result: List<NewsHeadlines>)

    fun updateHeadlines(result: List<NewsHeadlines>)

    fun showError(error: String)
}
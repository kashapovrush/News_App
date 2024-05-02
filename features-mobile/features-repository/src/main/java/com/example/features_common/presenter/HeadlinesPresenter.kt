package com.example.features_common.presenter

import android.util.Log
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.dbModel.NewsHeadlinesDb
import com.example.features_common.mapper.NewsHeadlinesMapper
import com.kashapovrush.api.modelsDto.toNewsHeadlinesListEntity
import com.kashapovrush.api.network.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class HeadlinesPresenter @Inject constructor(
    private val apiService: ApiService
) : MvpPresenter<HeadlinesView>() {

    private lateinit var disposableLoading: Disposable
    private lateinit var disposableUpdate: Disposable

    fun loadHeadlines(category: String) {
        viewState.loading()
        disposableLoading =
            apiService.getHeadlines("us", category, "5e9ef018060345fc864ba15895a7956f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showResult(it.articles.toNewsHeadlinesListEntity())
                }, {
                    viewState.showError(it.message ?: "")
                })
    }

    fun updateHeadlines(category: String) {
        disposableUpdate =
        apiService.getHeadlines("us", category, "5e9ef018060345fc864ba15895a7956f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showResult(it.articles.toNewsHeadlinesListEntity())
            }, {
                viewState.showError(it.message ?: "")
            })
    }

    override fun detachView(view: HeadlinesView?) {
        super.detachView(view)
    }

}
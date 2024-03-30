package com.example.features_common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.database.dao.NewsHeadlinesDao
import com.example.database.dbModel.NewsHeadlinesDb
import com.example.features_common.mapper.NewsHeadlinesMapper
import com.kashapovrush.api.modelsDto.NewsHeadlines
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val newsPostDao: NewsHeadlinesDao,
    private val mapper: NewsHeadlinesMapper
) : ViewModel() {

    private val listNew = mutableListOf<NewsHeadlines>()

    val newsList = newsPostDao.getList()
        .map {list ->
        listNew.clear()
            val filteredList = list.filter { it.isFavourite != 0 }
            val transformedList = mutableListOf<NewsHeadlines>()
                filteredList.forEach {
                    transformedList.add(mapper.mapDbModelToDto(it))
                }
            transformedList
    }.asLiveData()


    fun addNewsPost(post: NewsHeadlinesDb) {
        viewModelScope.launch {
            newsPostDao.addNewsPost(post)
        }
    }

    fun deleteNewsPost(title: String) {
        viewModelScope.launch {
            newsPostDao.deleteNewsPost(title)
        }
    }
}
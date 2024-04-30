package com.kashapovrush.api.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto
import com.kashapovrush.api.modelsDto.toNewsHeadlinesEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FilteredNewsPageSource @AssistedInject constructor(
    private val apiService: ApiService,
    @Assisted("query") private val query: String,
    @Assisted("language") private val language: String? = null,
    @Assisted("from") private val from: String? = null,
    @Assisted("to") private val to: String? = null,
    @Assisted("sortBy") private val sortBy: String? = null,
    @Assisted("apiKey") private val apiKey: String = ""
) : PagingSource<Int, NewsHeadlines>() {

    override fun getRefreshKey(state: PagingState<Int, NewsHeadlines>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsHeadlines> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)

        val response =
            apiService.filteredNews(
                query = query,
                page = page,
                pageSize = pageSize,
                apiKey = apiKey,
                sortBy = sortBy,
                language = language,
                from = from,
                to = to
            )
        if (response.isSuccessful) {
            val articles =
                checkNotNull(response.body()?.articles?.map { it.toNewsHeadlinesEntity() })
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(articles, prevKey, nextKey)
        } else {
            return LoadResult.Error(retrofit2.HttpException(response))
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("query") query: String,
            @Assisted("language") language: String? = null,
            @Assisted("from") from: String? = null,
            @Assisted("to") to: String? = null,
            @Assisted("sortBy") sortBy: String? = null,
            @Assisted("apiKey") apiKey: String = ""
        ): FilteredNewsPageSource
    }
}



package com.kashapovrush.api.network

import androidx.annotation.IntRange
import com.kashapovrush.api.modelsDto.NewsApiResponse
import com.kashapovrush.api.modelsDto.Source
import com.kashapovrush.api.modelsDto.Sources
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country: String,
        @Query ("category") category: String,
        @Query("apiKey") apiKey: String
    ): Observable<NewsApiResponse>

    @GET("everything")
    fun searchHeadlines(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Observable<NewsApiResponse>

    @GET("everything")
    suspend fun filteredNews(
        @Query("q") query: String? = null,
        @Query("language") language: String? = null,
        @Query ("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("apiKey") apiKey: String = "",
        @Query("pageSize") @IntRange(from = 1, to = 20) pageSize: Int = 20,
        @Query("page") @IntRange(from = 1) page: Int = 1
    ): Response<NewsApiResponse>

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String
    ): Sources

    @GET("everything")
    suspend fun getSourceNews(
        @Query("sources") sources: String?,
        @Query("apiKey") apiKey: String
    ): NewsApiResponse

}
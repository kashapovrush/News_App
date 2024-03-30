package com.kashapovrush.api.network

import com.kashapovrush.api.modelsDto.NewsApiResponse
import com.kashapovrush.api.modelsDto.Source
import com.kashapovrush.api.modelsDto.Sources
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
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
        @Query("language") language: String,
        @Query ("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsApiResponse

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
package com.kashapovrush.api.modelsDto

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = 0,
    @SerializedName("articles")
    var articles: List<NewsHeadlines>
)

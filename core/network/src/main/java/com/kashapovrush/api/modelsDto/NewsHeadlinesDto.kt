package com.kashapovrush.api.modelsDto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsHeadlinesDto(
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlToImage: String? = "",
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String?
) : Parcelable

fun NewsHeadlinesDto.toNewsHeadlinesEntity() =
    NewsHeadlines(
        source = source,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )

fun List<NewsHeadlinesDto>.toNewsHeadlinesListEntity() = map { it.toNewsHeadlinesEntity() }

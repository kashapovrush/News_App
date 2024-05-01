package com.kashapovrush.api.modelsDto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsHeadlines(
    val source: SourceDto,
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String?,
    val content: String?
): Parcelable

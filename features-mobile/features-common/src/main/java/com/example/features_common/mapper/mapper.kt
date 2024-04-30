package com.example.features_common.mapper

import com.example.database.dbModel.NewsHeadlinesDb
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.Source

fun NewsHeadlinesDb.toNewsHeadlines() = NewsHeadlines(
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    source = Source(name = source)

)
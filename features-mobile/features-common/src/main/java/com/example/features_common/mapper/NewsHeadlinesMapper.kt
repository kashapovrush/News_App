package com.example.features_common.mapper

import com.example.database.dbModel.NewsHeadlinesDb
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.Source
import javax.inject.Inject

class NewsHeadlinesMapper @Inject constructor() {

    fun mapDbModelToDto(newsHeadlinesDb: NewsHeadlinesDb): NewsHeadlines {
        return NewsHeadlines(
            title = newsHeadlinesDb.title,
            description = newsHeadlinesDb.description,
            source = Source(name = newsHeadlinesDb.source),
            content = newsHeadlinesDb.content,
            publishedAt = newsHeadlinesDb.publishedAt,
            urlToImage = newsHeadlinesDb.urlToImage,
            url = newsHeadlinesDb.url
        )
    }

    fun mapDtoToDbModel (newsHeadlines: NewsHeadlines): NewsHeadlinesDb {
        return NewsHeadlinesDb(
            title = newsHeadlines.title ?: "",
            source = newsHeadlines.source.name ?: "",
            description = newsHeadlines.description,
            content = newsHeadlines.content,
            url = newsHeadlines.url,
            urlToImage = newsHeadlines.urlToImage,
            publishedAt = newsHeadlines.publishedAt,
            isFavourite = 0,
            deleteTime = 0
        )
    }

    fun mapListDtoModelTOListDbModel(list: List<NewsHeadlines>): List<NewsHeadlinesDb> {
        return list.map {
            mapDtoToDbModel(it)
        }
    }

    fun mapListDbModelTOListDtoModel(list: List<NewsHeadlinesDb>): List<NewsHeadlines> {
        return list.map {
            mapDbModelToDto(it)
        }
    }
}
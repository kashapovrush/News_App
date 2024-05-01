package com.example.features_common.mapper

import com.example.database.dbModel.NewsHeadlinesDb
import com.example.database.dbModel.SourceDb
import com.example.features_common.models.Source
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto
import com.kashapovrush.api.modelsDto.SourceDto

import javax.inject.Inject

class NewsHeadlinesMapper @Inject constructor() {

    fun mapDbModelToDto(newsHeadlinesDb: NewsHeadlinesDb): NewsHeadlinesDto {
        return NewsHeadlinesDto(
            title = newsHeadlinesDb.title,
            description = newsHeadlinesDb.description,
            source = SourceDto(name = newsHeadlinesDb.source),
            content = newsHeadlinesDb.content,
            publishedAt = newsHeadlinesDb.publishedAt,
            urlToImage = newsHeadlinesDb.urlToImage,
            url = newsHeadlinesDb.url
        )
    }

    fun mapDtoToDbModel (newsHeadlines: NewsHeadlinesDto): NewsHeadlinesDb {
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

    fun mapListDtoModelTOListDbModel(list: List<NewsHeadlinesDto>): List<NewsHeadlinesDb> {
        return list.map {
            mapDtoToDbModel(it)
        }
    }

    fun mapListDbModelTOListDtoModel(list: List<NewsHeadlinesDb>): List<NewsHeadlinesDto> {
        return list.map {
            mapDbModelToDto(it)
        }
    }

    fun mapSourceDtoToSourceDb(dto: SourceDto): SourceDb {
        return SourceDb(
            id = dto.id ?: "",
            name = dto.name ?: "",
            category = dto.category ?: "",
            country = dto.country ?: ""
        )
    }

    fun mapSourcesListDtoToSourcesListDb(list: List<SourceDto>): List<SourceDb> {
        return list.map { mapSourceDtoToSourceDb(it) }
    }

    fun mapSourceDbToSourceEntity(db: SourceDb): Source {
        return Source(
            id = db.id,
            name = db.name,
            country = db.country,
            category = db.category
        )
    }

    fun mapSourcesDbToSourcesEntities(list: List<SourceDb>): List<Source> {
        return list.map { sourceDb ->
            mapSourceDbToSourceEntity(sourceDb)
        }
    }


    fun mapSourceToSourceDb(entity: Source): SourceDb {
        return SourceDb(
            id = entity.id ?: "",
            name = entity.name ?: "",
            category = entity.category ?: "",
            country = entity.country ?: ""
        )
    }

    fun mapSourcesListToSourcesListDb(list: List<Source>): List<SourceDb> {
        return list.map { mapSourceToSourceDb(it) }
    }

    fun mapSourceDtoToSourceEntity(dto: SourceDto): Source {
        return Source(
            id = dto.id ?: "",
            name = dto.name ?: "",
            category = dto.category ?: "",
            country = dto.country ?: ""
        )
    }

    fun mapSourcesDtoToSourcesEntities(list: List<SourceDto>): List<Source> {
        return list.map {
            mapSourceDtoToSourceEntity(it)
        }
    }

}
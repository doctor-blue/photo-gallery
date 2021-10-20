package com.devcomentry.photogallery.data.data_source.mappers

import com.devcomentry.photogallery.data.data_source.model.FileEntity
import com.devcomentry.photogallery.domain.mappers.DomainMapper
import com.devcomentry.photogallery.domain.model.FileModel

class FileEntityMapper : DomainMapper<FileEntity, FileModel> {
    override fun toDomain(entity: FileEntity): FileModel = FileModel(
        id = entity.id,
        idDatabase = entity.idDatabase,
        idFolder = entity.idFolder,
        name = entity.name,
        type = entity.type,
        uri = entity.uri,
        path = entity.path,
        size = entity.size,
        duration = entity.duration,
        timeCreated = entity.timeCreated,
        linkThumb = entity.linkThumb,
        width = entity.width,
        height = entity.height,
        isFavorite = entity.isFavorite,
        timeFile = entity.timeFile
    )

    override fun fromDomain(model: FileModel): FileEntity = FileEntity(
        id = model.id,
        idDatabase = model.idDatabase,
        idFolder = model.idFolder,
        name = model.name,
        type = model.type,
        uri = model.uri,
        path = model.path,
        size = model.size,
        duration = model.duration,
        timeCreated = model.timeCreated,
        linkThumb = model.linkThumb,
        width = model.width,
        height = model.height,
        isFavorite = model.isFavorite,
        timeFile = model.timeFile
    )

    fun toDomainList(list: List<FileEntity>): List<FileModel> = list.map {
        toDomain(it)
    }

    fun fromDomainList(list: List<FileModel>): List<FileEntity> = list.map {
        fromDomain(it)
    }
}
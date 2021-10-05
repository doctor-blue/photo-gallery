package com.devcomentry.photogallery.data.data_source.mappers

import com.devcomentry.photogallery.data.data_source.model.DummiesEntity
import com.devcomentry.photogallery.domain.mappers.DomainMapper
import com.devcomentry.photogallery.domain.model.Dummy
import javax.inject.Inject

class DummiesEntityMapper
@Inject
constructor() : DomainMapper<DummiesEntity, Dummy> {

    override fun toDomain(entity: DummiesEntity): Dummy {
        return Dummy(
            entity.id,
            entity.name,
            entity.desc
        )
    }

    override fun fromDomain(model: Dummy): DummiesEntity {
        return DummiesEntity(
            model.id,
            model.name,
            model.desc
        )
    }

    fun toDomainList(list: List<DummiesEntity>): List<Dummy> = list.map {
        toDomain(it)
    }

    fun fromDomainList(list: List<Dummy>): List<DummiesEntity> = list.map {
        fromDomain(it)
    }

}
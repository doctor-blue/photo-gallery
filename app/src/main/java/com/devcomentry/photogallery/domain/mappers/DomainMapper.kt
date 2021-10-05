package com.devcomentry.photogallery.domain.mappers

interface DomainMapper<E, M> {
    fun toDomain(entity: E): M
    fun fromDomain(model: M): E
}
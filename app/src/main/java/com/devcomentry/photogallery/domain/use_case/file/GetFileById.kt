package com.devcomentry.photogallery.domain.use_case.file

import com.devcomentry.photogallery.domain.repository.FileRepository

class GetFileById(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(id: Long) = fileRepository.getFileById(id)
}
package com.devcomentry.photogallery.domain.use_case

import com.devcomentry.photogallery.domain.repository.FileRepository

class GetFileByType(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(type: Int) = fileRepository.getFileByType(type)
}
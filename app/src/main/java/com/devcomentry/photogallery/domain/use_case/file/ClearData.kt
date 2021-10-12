package com.devcomentry.photogallery.domain.use_case.file

import com.devcomentry.photogallery.domain.repository.FileRepository

data class ClearData(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke() = fileRepository.clearFile()

}
package com.devcomentry.photogallery.domain.use_case

import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.repository.FileRepository

class AddFile(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(fileModel: FileModel) = fileRepository.addFile(fileModel)
}
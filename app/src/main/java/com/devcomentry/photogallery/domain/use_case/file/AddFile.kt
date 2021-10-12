package com.devcomentry.photogallery.domain.use_case.file

import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.repository.FileRepository

class AddFile(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(fileModel: FileModel) = fileRepository.addFile(fileModel)

    suspend operator fun invoke(files: List<FileModel>) = fileRepository.addAllFile(files)

}
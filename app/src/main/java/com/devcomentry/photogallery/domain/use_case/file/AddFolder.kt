package com.devcomentry.photogallery.domain.use_case.file

import com.devcomentry.photogallery.domain.model.Folder
import com.devcomentry.photogallery.domain.repository.FileRepository

class AddFolder(
    private val fileRepository: FileRepository
) {

    suspend operator fun invoke(folders: List<Folder>) = fileRepository.addAllFolder(folders)

}
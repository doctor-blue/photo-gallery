package com.devcomentry.photogallery.domain.use_case

data class FileUseCases(
    val addFile: AddFile,
    val updateFile: UpdateFile,
    val removeFile: RemoveFile,
    val getFileById: GetFileById,
    val getFileByType: GetFileByType,
)
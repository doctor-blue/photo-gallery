package com.devcomentry.photogallery.domain.use_case.file

import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.repository.FileRepository
import com.devcomentry.photogallery.domain.utils.DataState
import com.devcomentry.photogallery.presention.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*

class GetFileByType(
    private val fileRepository: FileRepository
) {

    operator fun invoke(type: Int): Flow<DataState<DataLocal>> = flow {
        emit(DataState.Loading())
        val files =
            fileRepository.getFileByType(type)
        val folders = fileRepository.getFolder()

        val dates = mutableListOf<DateSelect>()
        val months = mutableListOf<DateSelect>()

        val dateMap = HashMap<String, String>()
        val monthMap = HashMap<String, String>()

        for (file in files) {
            val timeFile = file.timeFile
            var monthFile = "05/2021"
            try {
                monthFile = Constants.monthFormatter.format(file.timeCreated)
            } catch (e: Exception) {
            }
            if (!dateMap.containsKey(timeFile)) {
                dates.add(
                    DateSelect(
                        date = timeFile,
                        type = FileModel.IS_IMAGE,
                        time = file.timeCreated,
                        listIdFolder = arrayListOf(file.idFolder),
                        month = monthFile
                    )
                )
                dateMap[timeFile] = timeFile
            }

            if (!monthMap.containsKey(monthFile)) {
                months.add(
                    DateSelect(
                        date = timeFile,
                        type = FileModel.IS_IMAGE,
                        time = file.timeCreated,
                        listIdFolder = arrayListOf(file.idFolder),
                        month = monthFile
                    )
                )
                monthMap[monthFile] = monthFile
            }
        }

        val dataLocal =
            DataLocal(
                file = files.sortedByDescending { it.timeCreated }
                    .toMutableList(),
                folder = folders.sortedBy { it.name }.toMutableList(),
                listDate = dates.sortedByDescending { it.time }
                    .toMutableList(),
                listMonth = months.sortedByDescending { it.time }
                    .toMutableList()
            )

        emit(DataState.Success(dataLocal))
    }


    private suspend fun syncData(
        type: Int
    ) = withContext(Dispatchers.IO) {
        val promise = async {
            val files =
                fileRepository.getFileByType(type)
            if (!files.isNullOrEmpty()) {
                files.forEach { file ->
//                    val listFile = fileCacheDataSource.getFileByIdTransfer(transfer.idTransfer)
//                    if (!listFile.isNullOrEmpty()) {
//                        listFile.forEach {
//                            fileCacheDataSource.removeFile(it)
//                        }
//                    }
//                    transferFileCacheDataSource.removeTransferFile(transfer)
                }
            }
        }
        promise.await()
    }

}
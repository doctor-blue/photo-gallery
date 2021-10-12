package com.devcomentry.photogallery.presention.common

import android.app.Application
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.model.FileModel.Companion.IS_IMAGE
import com.devcomentry.photogallery.domain.model.Folder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class LocalDataViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {
    companion object {
        const val CHECK_ITEM_LOADING = 100
    }

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)

    private val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    private val uri = MediaStore.Files.getContentUri("external")

    private val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.RELATIVE_PATH,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.BUCKET_ID,
        )
    else arrayOf(
        MediaStore.Files.FileColumns.MEDIA_TYPE,
        MediaStore.Files.FileColumns.DATA,
        MediaStore.Files.FileColumns.TITLE,
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Files.FileColumns.BUCKET_ID,
    )
    private val _allImage = MutableLiveData<DataLocal>()
    val allImage: LiveData<DataLocal>
        get() = _allImage

    suspend fun getImages() = withContext(Dispatchers.Default) {
        val arrMedia = ArrayList<FileModel>()
        val arrFolder = ArrayList<Folder>()
        val folders = HashMap<Long, String>()
        val arrDate = ArrayList<DateSelect>()
        val dateMap = HashMap<String, String>()

        var check = 0

        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)

        val cursor =
            application.contentResolver.query(
                uri,
                projection,
                selection,
                null,
                sortOrder
            )
        if (cursor != null && cursor.count > 0) {

            while (cursor.moveToNext()) {
                try {
                    val idMedia: Long =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                    val nameMedia: String =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    MediaStore.Files.FileColumns.DISPLAY_NAME
                                )
                            )
                        } else {
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    MediaStore.Files.FileColumns.TITLE
                                )
                            )
                        }

                    val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        "sdcard/" + cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH)
                        ) + cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                        )
                    } else {
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                        )
                    }

                    val contentUri = Uri.withAppendedPath(uri, "" + idMedia)
                    val folderIdIndex: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
                    val folderNameIndex: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                    val folderId: Long = cursor.getLong(folderIdIndex)
                    val timeModified = File(path).lastModified()

                    var timeFile = "14/05/2021"
                    try {
                        timeFile = formatter.format(timeModified)
                    } catch (e: Exception) {
                    }

                    if (File(path).exists() && File(path).length() > 5000) {
                        if (!folders.containsKey(folderId)) {
                            val folderName: String = cursor.getString(folderNameIndex)
                            val folder = Folder(folderId, folderName, 1)
                            folders[folderId] = folderName
                            arrFolder.add(folder)
                        } else {
                            arrFolder.find {
                                it.id == folderId
                            }?.let {
                                it.size = it.size + 1
                            }
                        }
                        arrMedia.add(
                            FileModel(
                                id = idMedia,
                                idFolder = folderId,
                                name = nameMedia,
                                uri = contentUri.toString(),
                                path = path,
                                timeCreated = timeModified,
                                type = IS_IMAGE,
                                size = File(path).length().toFloat()
                            )
                        )

                        if (!dateMap.containsKey(timeFile)) {
                            arrDate.add(
                                DateSelect(
                                    date = timeFile,
                                    type = IS_IMAGE,
                                    time = timeModified,
                                    listIdFolder = arrayListOf(folderId)
                                )
                            )
                            dateMap[timeFile] = timeFile
                        }

                        check++
                        if (check == CHECK_ITEM_LOADING) {
                            check = 0
                            withContext(Dispatchers.Main) {
                                _allImage.postValue(
                                    DataLocal(
                                        file = arrMedia.sortedByDescending { it.timeCreated }
                                            .toMutableList(),
                                        folder = arrFolder.sortedBy { it.name }.toMutableList(),
                                        listDate = arrDate.sortedByDescending { it.time }
                                            .toMutableList()
                                    )
                                )

                            }
                        }
                    }
                } catch (ex: Exception) {
                }
            }
            cursor.close()
            folders.clear()
            dateMap.clear()
        }
        withContext(Dispatchers.Main) {
            val a = DataLocal(
                file = arrMedia.sortedByDescending { it.timeCreated }.toMutableList(),
                folder = arrFolder.sortedBy { it.name }.toMutableList(),
                listDate = arrDate.sortedByDescending { it.time }.toMutableList()
            )
            Log.d("DataLocal", "getImages: $a")
            _allImage.postValue(
             a
            )

        }
    }

}
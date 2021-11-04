package com.devcomentry.photogallery.presention.utils

import android.app.RecoverableSecurityException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.IntentSender.SendIntentException
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.Lifecycle
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.domain.model.FileModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun performDeleteImage(
    images: List<FileModel>,
    context: Context,
    intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>? = null,
    isHide: Boolean = false,
    dialogMesRes: Int = R.string.are_you_sure_want_to_delete,
    lifecycle: Lifecycle,
    onImageRemoved: () -> Unit
) {
    CoroutineScope(Dispatchers.Main).launch {
        try {
            /**
             * In [Build.VERSION_CODES.Q] and above, it isn't possible to modify
             * or delete items in MediaStore directly, and explicit permission
             * must usually be obtained to do this.
             *
             * The way it works is the OS will throw a [RecoverableSecurityException],
             * which we can catch here. Inside there's an [IntentSender] which the
             * activity can use to prompt the user to grant permission to the item
             * so it can be either updated or deleted.
             */
            val onDelete: () -> Unit = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val sender = MediaStore.createDeleteRequest(
                        context.contentResolver,
                        images.map { Uri.parse(it.uri) }
                    ).intentSender
                    try {
                        intentSenderLauncher?.launch(
                            IntentSenderRequest.Builder(sender).build()
                        )
                    } catch (e: SendIntentException) {
                    }

                } else {
                    for (i in images.indices) {
                        val image = images[i]
                        context.contentResolver.delete(
                            Uri.parse(image.uri),
                            "${MediaStore.Images.Media._ID} = ?",
                            arrayOf(image.id.toString())
                        )
                    }
                    context.showToast(R.string.file_deleted_mess)
                    onImageRemoved()
                }
            }
            val titleRes = if (isHide) R.string.alert else R.string.are_you_sure
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (isHide) {
                    context.showDialogDelete(
                        lifecycle,
                        resTitle = titleRes,
                        resContent = dialogMesRes,
                        onCancel = {
                        }) {
                        onDelete()
                    }
                } else {
                    onDelete()
                }
            } else {
                context.showDialogDelete(
                    lifecycle,
                    resTitle = titleRes,
                    resContent = dialogMesRes,
                    onCancel = {
                    }) {
                    onDelete()
                }
            }


        } catch (securityException: SecurityException) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val recoverableSecurityException =
                    securityException as? RecoverableSecurityException
                        ?: throw securityException

                // Signal to the Activity that it needs to request permission and
                // try the delete again if it succeeds.
                recoverableSecurityException.userAction.actionIntent.intentSender
            } else {
                throw securityException
            }
        }
    }
}

fun shareImageTo(imageUri: ArrayList<Uri>, context: Context) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND_MULTIPLE
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUri)
        type = "image/*"
    }
    context.startActivity(
        Intent.createChooser(
            shareIntent,
            context.getString(R.string.share_images_to)
        )
    )
}

fun formatFileSize(size: Float, context: Context): String {
    var folderSize = size
    var unit = context.getStringArray(R.array.memory_unit)[0]
    val oneGbFromB = 1_073_741_824.0f
    val oneMBFromB = 1_048_576.0f

    // >1MB dv = MB, <1MB dv = KB
    if (folderSize >= oneMBFromB && folderSize < oneGbFromB) {
        folderSize /= oneMBFromB
        unit = context.getStringArray(R.array.memory_unit)[1]
    } else if (folderSize >= oneGbFromB) {
        folderSize /= oneGbFromB
        unit = context.getStringArray(R.array.memory_unit)[2]
    } else {
        folderSize /= 1024
    }

    return (folderSize.decimalFormat() + unit)
}
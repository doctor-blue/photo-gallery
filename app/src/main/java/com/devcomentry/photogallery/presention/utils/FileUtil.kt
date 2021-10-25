package com.devcomentry.photogallery.presention.utils

import android.app.RecoverableSecurityException
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.devcomentry.photogallery.domain.model.FileModel
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun performDeleteImage(
    images: List<FileModel>,
    context: Context,
    onImageRemoved: () -> Unit
) {
    withContext(Dispatchers.IO) {
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
            for (i in images.indices) {
                val image = images[i]
                if (image.isSelected) {
                    context.contentResolver.delete(
                        Uri.parse(image.uri),
                        "${MediaStore.Images.Media._ID} = ?",
                        arrayOf(image.id.toString())
                    )
                }
            }
            withContext(Dispatchers.Main) {
                onImageRemoved()
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
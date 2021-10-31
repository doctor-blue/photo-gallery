package com.devcomentry.photogallery.presention.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.DialogCancelDeleteBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.FileModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.showDialogDelete(
    lifecycle: Lifecycle,
    dataLocal: DataLocal? = null,
    fileModel: FileModel? = null,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit,
) {
    var isShow = false
    val dialog: Dialog
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_cancel_delete, null)
    val builder = AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(false)


    dialog = builder.create()
    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val binding = DialogCancelDeleteBinding.bind(view)

    binding.tvCancel.setPreventDoubleClick {
        onCancel()
        dialog.dismiss()
    }
    binding.tvOk.setPreventDoubleClick {

        if (dataLocal != null) {
            CoroutineScope(Dispatchers.Main).launch {
                performDeleteImage(
                    dataLocal.file,
                    applicationContext
                ) {
                    onConfirm()
                }
            }
        }
        if (fileModel != null) {
            CoroutineScope(Dispatchers.Main).launch {
                performDeleteImage(
                    listOf(fileModel),
                    applicationContext
                ) {
                    onConfirm()
                }
            }
        }

        dialog.dismiss()
    }

    lifecycle.addObserver(LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                if (dialog.isShowing) {
                    isShow = true
                }
                dialog.dismiss()
            }

            Lifecycle.Event.ON_RESUME -> {
                if (isShow) {
                    dialog.show()
                }
            }
            else -> {

            }
        }
    })
    if (!dialog.isShowing) {
        dialog.show()
    }
}

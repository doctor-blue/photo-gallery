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
import com.devcomentry.photogallery.databinding.DialogSetPasswordBinding
import com.devcomentry.photogallery.databinding.DialogTypePasswordBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.FileModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.showDialogDelete(
    lifecycle: Lifecycle,
    resContent: Int = R.string.are_you_sure_want_to_delete,
    resTitle: Int = R.string.are_you_sure,
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
    binding.tvContent.setText(resContent)
    binding.txtTitle.setText(resTitle)

    binding.tvCancel.setPreventDoubleClick {
        onCancel()
        dialog.dismiss()
    }
    binding.tvOk.setPreventDoubleClick {
        onConfirm()
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

fun Context.showDialogSetPassword(
    lifecycle: Lifecycle,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    var isShow = false
    val dialog: Dialog
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_set_password, null)
    val builder = AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(false)


    dialog = builder.create()
    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val binding = DialogSetPasswordBinding.bind(view)

    binding.txtCancel.setPreventDoubleClick {
        onCancel()
        dialog.dismiss()
    }
    binding.txtConfirm.setPreventDoubleClick {
        onConfirm()
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

fun Context.showDialogTypePassword(
    lifecycle: Lifecycle,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    var isShow = false
    val dialog: Dialog
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_type_password, null)
    val builder = AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(false)


    dialog = builder.create()
    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val binding = DialogTypePasswordBinding.bind(view)

    binding.txtCancel.setPreventDoubleClick {
        onCancel()
        dialog.dismiss()
    }
    binding.txtConfirm.setPreventDoubleClick {
        onConfirm()
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
package com.devcomentry.photogallery.presentation.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(
    messId:Int
){
    Toast.makeText(this,messId,Toast.LENGTH_SHORT).show()
}
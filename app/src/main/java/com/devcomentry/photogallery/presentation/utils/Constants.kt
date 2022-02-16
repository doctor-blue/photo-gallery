package com.devcomentry.photogallery.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    var isDataLoaded = false
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    val monthFormatter = SimpleDateFormat("MM/yyyy", Locale.ROOT)
    val timeFormatter = SimpleDateFormat("hh:mm", Locale.ROOT)
}
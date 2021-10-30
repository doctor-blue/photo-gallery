package com.devcomentry.photogallery.presention.utils

import java.text.DecimalFormat

fun Double.decimalFormat(): String =
    if (this == this.toInt().toDouble()) this.toInt().toString() else DecimalFormat("0.00").format(
        this
    )

fun Float.decimalFormat(): String =
    if (this == this.toInt().toFloat()) this.toInt().toString() else DecimalFormat("0.00").format(
        this
    )

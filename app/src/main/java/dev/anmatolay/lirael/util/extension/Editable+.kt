package dev.anmatolay.lirael.util.extension

import android.text.Editable

fun Editable.isValid(): Boolean =
    this.toString().run {
        isLetters() && !isNullOrBlank()
    }

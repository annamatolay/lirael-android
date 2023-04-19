package dev.anmatolay.lirael.util.extension

fun String?.isLetters() = this?.all { it.isLetter() } ?: false

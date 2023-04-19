package dev.anmatolay.lirael.util.extension

import androidx.fragment.app.Fragment
import dev.anmatolay.lirael.presentation.MainActivity

fun Fragment.mainActivity() = this.requireActivity() as MainActivity

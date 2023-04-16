package dev.anmatolay.lirael.util.extension

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dev.anmatolay.lirael.R

fun View.navigateTo(direction: NavDirections) =
    this.findNavController().navigate(direction)

fun Fragment.navigateTo(direction: NavDirections) = this.findNavController().navigate(direction)

fun Fragment.popBackStack() = this.findNavController().popBackStack()

fun Activity.navigateTo(direction: NavDirections) =
    this.findNavController(R.id.nav_host_fragment).navigate(direction)

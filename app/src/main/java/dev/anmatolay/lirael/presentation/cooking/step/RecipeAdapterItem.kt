package dev.anmatolay.lirael.presentation.cooking.step

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeAdapterItem(
    val step: Int,
    val instructionsLastIndex: Int,
    val title: String,
    val instruction: String,
    val isLastItem: Boolean,
): Parcelable

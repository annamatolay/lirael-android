package dev.anmatolay.lirael.util.extension

import android.os.Build
import android.os.Bundle
import dev.anmatolay.lirael.domain.model.Recipe
import dev.anmatolay.lirael.presentation.cooking.step.RecipeAdapterItem
import dev.anmatolay.lirael.util.Constants.KEY_OPENED_RECIPE
import dev.anmatolay.lirael.util.Constants.KEY_RECIPE_ADAPTER_ITEM

fun Bundle?.getRecipeParcelable(): Recipe? {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this?.getParcelable(KEY_OPENED_RECIPE, Recipe::class.java)
    else
        this?.getParcelable(KEY_OPENED_RECIPE)
}

fun Bundle?.getRecipeItemParcelable(): RecipeAdapterItem? {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this?.getParcelable(KEY_RECIPE_ADAPTER_ITEM, RecipeAdapterItem::class.java)
    else
        this?.getParcelable(KEY_RECIPE_ADAPTER_ITEM)
}

package dev.anmatolay.lirael.data.dto

import dev.anmatolay.lirael.domain.model.PresetRecipe

data class PresetRecipeDto(
    val title: String,
    val ingredients: String,
    val servings: String,
    val instructions: String,
) {

    fun toModel(category: PresetRecipe.Category): PresetRecipe {
        val ingredientsList = ingredients.split("|")
        val instructionsList = instructions.split(". ")
        return PresetRecipe(title, ingredientsList, instructionsList, servings, category)
    }
}

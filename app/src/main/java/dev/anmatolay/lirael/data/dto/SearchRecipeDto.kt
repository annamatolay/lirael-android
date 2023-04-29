package dev.anmatolay.lirael.data.dto

import dev.anmatolay.lirael.domain.model.Recipe
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

data class SearchRecipeDto(
    val title: String,
    val ingredients: String,
    val instructions: String,
    val servings: String,
):RecipeDto() {

    override fun toModel(): Recipe {
        val ingredientsList = ingredients.split("|")
        val instructionsList = instructions.split(". ")
        return Recipe(title, ingredientsList, instructionsList, servings)
    }
}

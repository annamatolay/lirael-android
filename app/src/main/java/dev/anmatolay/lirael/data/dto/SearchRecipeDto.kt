package dev.anmatolay.lirael.data.dto

import dev.anmatolay.lirael.domain.model.Recipe
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.util.regex.Pattern

data class SearchRecipeDto(
    val title: String,
    val ingredients: String,
    val instructions: String,
    val servings: String,
):RecipeDto() {

    override fun toModel(): Recipe {
        val ingredientsList = ingredients.split("|")
        // look behind, not number ending with a . and space
        val instructionsList = instructions.split(Regex("(?<=\\D[.] )"))
        return Recipe(title, ingredientsList, instructionsList, servings)
    }
}

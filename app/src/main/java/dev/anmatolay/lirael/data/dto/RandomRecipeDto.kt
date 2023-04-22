package dev.anmatolay.lirael.data.dto

import dev.anmatolay.lirael.domain.model.Recipe
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

data class RandomRecipeDto(
    val title: String,
    val ingredients: List<String>,
    val instructions: List<HowToStep>,
    val image: String = "",
): RecipeDto() {
    data class HowToStep(val text: String)

    override fun toModel(): Recipe {
        val instructionsList = mutableListOf<String>()
        instructions.forEach { instructionsList.add(it.text) }
        return Recipe(title, ingredients, instructionsList, image.toHttpUrlOrNull())
    }
}

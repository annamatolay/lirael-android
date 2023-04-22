package dev.anmatolay.lirael.presentation.recipes

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.domain.model.Recipe

data class RecipesState(
    val recipes: List<Recipe>? = null,
    val isRecipesLoading: Boolean = true,
    val error: Error? = null,
): UiState {
    enum class Error {
        NOT_FOUND,
        API_ERROR,
        DB_ERROR,
    }
}

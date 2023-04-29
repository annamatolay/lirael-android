package dev.anmatolay.lirael.presentation.favourites

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.domain.model.Recipe

data class FavouriteState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean = false,
    val error: Error? = null,
): UiState {

    enum class Error {
        DB_READ_ERROR,
        DB_DELETE_ERROR,
    }
}

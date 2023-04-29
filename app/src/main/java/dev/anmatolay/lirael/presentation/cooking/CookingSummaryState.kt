package dev.anmatolay.lirael.presentation.cooking

import dev.anmatolay.lirael.core.presentation.UiState

data class CookingSummaryState(
    val isLoading: Boolean = false,
    val isSaved: Boolean? = null,
    val error: Error? = null,
): UiState {

    enum class Error {
        DB_READ_ERROR,
        DB_CREATE_ERROR,
        DB_DELETE_ERROR,
    }
}

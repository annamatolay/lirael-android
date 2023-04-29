package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.UiState

data class CookingStepState(
    val isPositiveButtonLoading: Boolean = false,
    val isPositiveButtonEnabled: Boolean = true,
    val isNeutralButtonLoading: Boolean = false,
    val isUpdateDone: Boolean = false,
    val error: Error? = null,
): UiState {
    enum class Error {
        USER_STAT_UPDATE_FAILED,
        RECIPE_DB_CREATE_ERROR,
    }
}

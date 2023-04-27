package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.UiState

data class CookingStepState(
    val isLoading: Boolean = false,
    val isUpdateDone: Boolean = false,
    val error: Error? = null,
): UiState {
    enum class Error {
        USER_STAT_UPDATE_FAILED
    }
}

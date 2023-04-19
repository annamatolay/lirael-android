package dev.anmatolay.lirael.presentation.settings

import dev.anmatolay.lirael.core.presentation.UiState

data class SettingsState(
    val isUserDeletionComplete: Boolean = false,
    val error: Error? = null,
) : UiState {

    enum class Error {
        USER_DELETION_ERROR
    }
}

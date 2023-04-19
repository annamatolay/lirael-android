package dev.anmatolay.lirael.presentation.onboarding.welcome

import dev.anmatolay.lirael.core.presentation.UiState

data class WelcomeState(
    val isLoading: Boolean = false,
    val isUserCreationCompleted: Boolean = false,
    val error: Error? = null
): UiState {
    enum class Error {
        USER_CREATION_ERROR
    }
}

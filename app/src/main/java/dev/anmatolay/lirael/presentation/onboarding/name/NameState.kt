package dev.anmatolay.lirael.presentation.onboarding.name

import dev.anmatolay.lirael.core.presentation.UiState

data class NameState(
    val isLoading: Boolean = false,
    val isUpdateCompleted: Boolean = false,
): UiState

package dev.anmatolay.lirael.presentation.dialog

import dev.anmatolay.lirael.core.presentation.UiState

data class ExitConfirmationDialogState(
    val isGone: Boolean = false
) : UiState

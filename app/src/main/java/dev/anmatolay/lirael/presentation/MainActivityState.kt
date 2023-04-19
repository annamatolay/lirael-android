package dev.anmatolay.lirael.presentation

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.presentation.dialog.exit.ExitConfirmationDialogState

data class MainActivityState(
    val exitConfirmationDialogState: ExitConfirmationDialogState,
    val isDarkModeEnabled: Boolean,
) : UiState

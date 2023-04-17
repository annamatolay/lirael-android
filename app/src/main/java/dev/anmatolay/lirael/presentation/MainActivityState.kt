package dev.anmatolay.lirael.presentation

import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.presentation.dialog.ExitConfirmationDialogState

data class MainActivityState(
    val exitConfirmationDialogState: ExitConfirmationDialogState
) : UiState

package dev.anmatolay.lirael.presentation.dialog.deletion

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class DeletionConfirmationEvent: UiEvent {
    object DeletionConfirmed: DeletionConfirmationEvent()
    object RetryDeleteUserOnClicked: DeletionConfirmationEvent()
}

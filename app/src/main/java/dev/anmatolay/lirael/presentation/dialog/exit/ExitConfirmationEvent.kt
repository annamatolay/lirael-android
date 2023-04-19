package dev.anmatolay.lirael.presentation.dialog.exit

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class ExitConfirmationEvent: UiEvent {
    class NotShowDialogCheckboxChanged(val isChecked: Boolean): ExitConfirmationEvent()
}

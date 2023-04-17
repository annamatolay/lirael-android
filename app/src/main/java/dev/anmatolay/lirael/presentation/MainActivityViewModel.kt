package dev.anmatolay.lirael.presentation

import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.core.presentation.UiState
import dev.anmatolay.lirael.presentation.dialog.ExitConfirmationDialogState
import dev.anmatolay.lirael.util.Constants

class MainActivityViewModel(
    private val sharedPrefHandler: SharedPrefHandler,
) : BaseUdfViewModel<MainActivityState, Event>() {

    override fun onViewCreated() {
        super.onViewCreated()

        val isGone = sharedPrefHandler.getBoolean(Constants.KEY_HIDE_EXIT_DIALOG)
        triggerUiStateChange(
            MainActivityState(ExitConfirmationDialogState(isGone))
        )
    }
}

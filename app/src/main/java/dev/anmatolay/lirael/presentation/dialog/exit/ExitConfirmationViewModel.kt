package dev.anmatolay.lirael.presentation.dialog.exit

import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.util.Constants.KEY_HIDE_EXIT_DIALOG

class ExitConfirmationViewModel(
    private val sharedPrefHandler: SharedPrefHandler
) : BaseUdfViewModel<ExitConfirmationDialogState, ExitConfirmationEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        doOnUiEventReceived { event ->
            when (event) {
                is ExitConfirmationEvent.NotShowDialogCheckboxChanged -> {
                    sharedPrefHandler.putBoolean(KEY_HIDE_EXIT_DIALOG, event.isChecked)
                    if (event.isChecked)
                        triggerUiStateChange(ExitConfirmationDialogState(isGone = true))
                }
            }
        }.subscribe().disposeOnDestroy()
    }
}

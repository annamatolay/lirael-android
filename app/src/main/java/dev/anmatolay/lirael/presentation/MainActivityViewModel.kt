package dev.anmatolay.lirael.presentation

import android.content.res.Resources
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.presentation.dialog.exit.ExitConfirmationDialogState
import dev.anmatolay.lirael.util.Constants

class MainActivityViewModel(
    private val sharedPrefHandler: SharedPrefHandler,
    private val resources: Resources,
) : BaseUdfViewModel<MainActivityState, MainActivityEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        val uiModePrefKey = resources.getString(R.string.key_pref_ui_mode)
        val isGone = sharedPrefHandler.getBoolean(Constants.KEY_HIDE_EXIT_DIALOG)
        val isDarkModeEnabled = sharedPrefHandler.getBoolean(uiModePrefKey)
        triggerUiStateChange(
            MainActivityState(ExitConfirmationDialogState(isGone), isDarkModeEnabled)
        )

        doOnUiEventReceived { event ->
            when (event) {
                is MainActivityEvent.UiModeChanged ->
                    sharedPrefHandler.putBoolean(uiModePrefKey, event.isDarkModeEnabled)
            }
        }.subscribe().disposeOnDestroy()
    }
}

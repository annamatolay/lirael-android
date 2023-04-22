package dev.anmatolay.lirael.presentation.onboarding.diet

import android.content.res.Resources
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.State

class DietViewModel(
    private val resources: Resources,
    private val sharedPrefHandler: SharedPrefHandler,
) : BaseUdfViewModel<State, Event>() {

    override fun onViewResumed() {
        super.onViewResumed()


        // FIXME: diet data not persisted in shared pref correctly (Settings not picking it up)
        doOnUiEventReceived {uiEvent ->
            when(uiEvent) {
                is DietEvent.DietSelected -> {
                    val value = resources.getStringArray(R.array.diet_values)[uiEvent.id]
                    sharedPrefHandler.putString(resources.getString(R.string.key_pref_diet), value)
                }
            }
        }.subscribe().disposeOnPause()
    }
}

package dev.anmatolay.lirael.presentation.onboarding.premium

import android.content.res.Resources
import android.os.Bundle
import com.google.firebase.analytics.ktx.ParametersBuilder
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.analytics.AnalyticsConstants.KEY_ANALYTICS_PREMIUM
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel

class PremiumViewModel(
    private val resources: Resources,
    private val sharedPrefHandler: SharedPrefHandler,
    private val analyticsWrapper: AnalyticsWrapper,
) : BaseUdfViewModel<PremiumState, PremiumEvent>() {
    override fun onViewResumed() {
        super.onViewResumed()

        triggerUiStateChange(PremiumState())

        // FIXME: premium data not persisted in shared pref correctly (Settings not picking it up)
        val prefKey = resources.getString(R.string.key_pref_premium)
        doOnUiEventReceived { event ->
            when (event) {
                is PremiumEvent.OnPositiveButtonClicked -> {
                    sharedPrefHandler.putBoolean(prefKey, true)
                    analyticsWrapper.logEven(KEY_ANALYTICS_PREMIUM) {
                        it.param(KEY_ANALYTICS_PREMIUM, Value.INTERESTED.name)
                    }
                }
                is PremiumEvent.OnNegativeButtonClicked -> {
                    sharedPrefHandler.putBoolean(prefKey, false)
                    analyticsWrapper.logEven(KEY_ANALYTICS_PREMIUM) {
                        it.param(KEY_ANALYTICS_PREMIUM, Value.NOT_INTERESTED.name)
                    }
                }
                is PremiumEvent.OnNeutralButtonClicked ->
                    analyticsWrapper.logEven(KEY_ANALYTICS_PREMIUM) {
                        it.param(KEY_ANALYTICS_PREMIUM, Value.NOT_SURE.name)
                    }
            }
            triggerUiStateChange(PremiumState(isDataPersisted = true))
        }.subscribe().disposeOnDestroy()
    }

}

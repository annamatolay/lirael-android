package dev.anmatolay.lirael.core.analytics.impl

import com.google.firebase.analytics.ktx.ParametersBuilder
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper

class FakeAnalyticsImpl : AnalyticsWrapper {
    override fun setAnalyticsCollectionEnabled(isEnabled: Boolean) {
        // Do nothing
    }

    override fun setUserId(userId: String?) {
        // Do nothing
    }

    override fun setUserProperty(name: String, value: String) {
        // Do nothing
    }


    override fun logEven(name: String, params: (ParametersBuilder) -> Unit) {
        // Do nothing
    }

}

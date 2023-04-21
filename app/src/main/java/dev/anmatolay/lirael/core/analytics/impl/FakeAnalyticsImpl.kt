package dev.anmatolay.lirael.core.analytics.impl

import android.os.Bundle
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


    override fun logEven(name: String, bundle: Bundle) {
        // Do nothing
    }

}

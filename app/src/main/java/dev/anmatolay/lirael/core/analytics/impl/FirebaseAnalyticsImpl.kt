package dev.anmatolay.lirael.core.analytics.impl

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.ParametersBuilder
import com.google.firebase.analytics.ktx.logEvent
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper

class FirebaseAnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsWrapper {

    override fun setAnalyticsCollectionEnabled(isEnabled: Boolean) = firebaseAnalytics.setAnalyticsCollectionEnabled(isEnabled)
    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }


    override fun logEven(name: String, builder: (ParametersBuilder) -> Unit) =
        firebaseAnalytics.logEvent(name) { builder(this) }

}

package dev.anmatolay.lirael.core.analytics.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper

class FirebaseAnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsWrapper {

    override fun setAnalyticsCollectionEnabled(isEnabled: Boolean) = firebaseAnalytics.setAnalyticsCollectionEnabled(isEnabled)
    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }


    override fun logEven(name: String, bundle: Bundle) =
        firebaseAnalytics.logEvent(name, bundle)

}

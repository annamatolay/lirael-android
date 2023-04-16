package dev.anmatolay.lirael.core.analytic.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dev.anmatolay.lirael.core.analytic.AnalyticsWrapper

class FirebaseAnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsWrapper {

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }


    override fun logEven(name: String, bundle: Bundle) =
        firebaseAnalytics.logEvent(name, bundle)

}

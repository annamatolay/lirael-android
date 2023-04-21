package dev.anmatolay.lirael.core.analytics

import android.os.Bundle

interface AnalyticsWrapper {

    fun setAnalyticsCollectionEnabled(isEnabled: Boolean)

    fun setUserId(userId: String?)

    fun setUserProperty(name: String, value: String)

    fun logEven(name: String, bundle: Bundle)
}

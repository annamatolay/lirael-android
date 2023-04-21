package dev.anmatolay.lirael.core.analytics

import com.google.firebase.analytics.ktx.ParametersBuilder

interface AnalyticsWrapper {

    fun setAnalyticsCollectionEnabled(isEnabled: Boolean)

    fun setUserId(userId: String?)

    fun setUserProperty(name: String, value: String)

    fun logEven(name: String, params: (ParametersBuilder) -> Unit)
}

package dev.anmatolay.lirael.domain.usecase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.logging.CrashlyticsLogTree
import dev.anmatolay.lirael.core.logging.DiamondDebugTree
import dev.anmatolay.lirael.util.UserProperty
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_ANDROID_VERSION
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_API_LEVEL
import dev.anmatolay.lirael.util.UserProperty.Companion.KEY_APP_VERSION
import timber.log.Timber

class MonitoringUseCase(
    private val analyticsWrapper: AnalyticsWrapper,
    private val crashlytics: FirebaseCrashlytics,
    private val userProperty: UserProperty,
) {
    fun setUserProperties() {
        analyticsWrapper.setUserProperty(KEY_APP_VERSION, userProperty.version)
        analyticsWrapper.setUserProperty(KEY_ANDROID_VERSION, userProperty.osVersion)
        analyticsWrapper.setUserProperty(KEY_API_LEVEL, userProperty.sdkVersion.toString())
    }

    fun setUpAnalyticsAndLogging(userId: String?) {
        analyticsWrapper.setUserId(userId)
        // If Timber already set by Application, remove to avoid duplicated logs
        Timber.uprootAll()
        Timber.plant(
            if (BuildConfig.DEBUG)
                DiamondDebugTree()
            else
                CrashlyticsLogTree(crashlytics, userId)
        )
    }

    fun isCrashlyticsCollectionEnabled(isEnabled: Boolean) = crashlytics.setCrashlyticsCollectionEnabled(isEnabled)
}

package dev.anmatolay.lirael.util

import android.os.Build
import dev.anmatolay.lirael.BuildConfig

interface UserProperty {
    val version: String
    val osVersion: String
    val sdkVersion: Int

    companion object {
        const val KEY_APP_VERSION = "version_name"
        const val KEY_ANDROID_VERSION = "android_version"
        const val KEY_API_LEVEL = "api_level"
    }
}

object UserPropertyImpl : UserProperty {
    override val version = BuildConfig.VERSION_NAME
    override val osVersion: String = Build.VERSION.RELEASE
    override val sdkVersion = Build.VERSION.SDK_INT

}

// android.os.Build not available during unit tests
object MockUserPropertyImpl : UserProperty {
    const val TEST_VALUE_STRING = "mock"
    const val TEST_VALUE_INT = 1

    override val version = TEST_VALUE_STRING
    override val osVersion = TEST_VALUE_STRING
    override val sdkVersion = TEST_VALUE_INT
}


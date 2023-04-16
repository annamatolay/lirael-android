package dev.anmatolay.lirael.core.network.interceptor

import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.util.UserProperty
import okhttp3.Interceptor
import org.koin.java.KoinJavaComponent.inject

val userAgentInterceptor = Interceptor { chain ->

    val userProperty by inject<UserProperty>(UserProperty::class.java)

    val name = BuildConfig.APPLICATION_ID.split(".").last()

    val request = chain.request().newBuilder()
        .header(
            "User-Agent",
            "$name v${userProperty.version} " +
                    "- Android ${userProperty.osVersion} (API ${userProperty.sdkVersion})",
        )
        .build()

    chain.proceed(request)
}

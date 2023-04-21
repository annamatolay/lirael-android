package dev.anmatolay.lirael.core.di.module

import android.content.res.Resources
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.analytics.impl.FirebaseAnalyticsImpl
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.impl.FirebaseAuthenticatorImpl
import dev.anmatolay.lirael.core.network.ApiClientFactory
import dev.anmatolay.lirael.core.network.MoshiFactory
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.core.threading.impl.SchedulerProviderImpl
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.util.UserProperty
import dev.anmatolay.lirael.util.UserPropertyImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<Authenticator> { FirebaseAuthenticatorImpl(get()) }
    factory<AnalyticsWrapper> { FirebaseAnalyticsImpl(get()) }
    factory<SchedulerProvider> { SchedulerProviderImpl() }
    single { MoshiFactory.create() }
    single { ApiClientFactory }
    factory { SharedPrefHandler(androidContext()) }
    factory<UserProperty> { UserPropertyImpl }
    factory<Resources> { androidContext().resources }
}

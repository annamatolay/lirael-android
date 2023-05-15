package dev.anmatolay.lirael.core.di.module

import android.content.res.Resources
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.network.ApiClientFactory
import dev.anmatolay.lirael.core.network.MoshiFactory
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.core.threading.impl.SchedulerProviderImpl
import dev.anmatolay.lirael.util.LocalDateProvider
import dev.anmatolay.lirael.util.LocalDateProviderImpl
import dev.anmatolay.lirael.util.UserProperty
import dev.anmatolay.lirael.util.UserPropertyImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<SchedulerProvider> { SchedulerProviderImpl() }
    single { MoshiFactory.create() }
    single { ApiClientFactory }
    factory { SharedPrefHandler(androidContext()) }
    factory<UserProperty> { UserPropertyImpl }
    factory<Resources> { androidContext().resources }
    factory<LocalDateProvider> { LocalDateProviderImpl() }
}

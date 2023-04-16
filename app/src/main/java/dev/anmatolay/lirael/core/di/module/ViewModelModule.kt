package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.presentation.MainActivityViewModel
import dev.anmatolay.lirael.presentation.dashboard.DashboardViewModel
import dev.anmatolay.lirael.presentation.home.HomeViewModel
import dev.anmatolay.lirael.presentation.notifications.NotificationsViewModel
import dev.anmatolay.lirael.presentation.splash.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainActivityViewModel() }
    factory { SplashViewModel(get()) }
    factory { HomeViewModel() }
    factory { DashboardViewModel() }
    factory { NotificationsViewModel() }
}

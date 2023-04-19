package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.presentation.MainActivityViewModel
import dev.anmatolay.lirael.presentation.recipes.RecipesViewModel
import dev.anmatolay.lirael.presentation.statistics.StatisticsViewModel
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import dev.anmatolay.lirael.presentation.dialog.ExitConfirmationViewModel
import dev.anmatolay.lirael.presentation.favourites.FavouritesViewModel
import dev.anmatolay.lirael.presentation.settings.SettingsViewModel
import dev.anmatolay.lirael.presentation.splash.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainActivityViewModel(get(), get()) }
    factory { SplashViewModel(get()) }
    factory { StatisticsViewModel(get(), get()) }
    factory { RecipesViewModel() }
    factory { FavouritesViewModel() }
    factory { CustomViewModel() }
    factory { ExitConfirmationViewModel(get()) }
    factory { SettingsViewModel(get(), get()) }
}

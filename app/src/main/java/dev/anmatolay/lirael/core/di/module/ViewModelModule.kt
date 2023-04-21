package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.presentation.MainActivityViewModel
import dev.anmatolay.lirael.presentation.recipes.RecipesViewModel
import dev.anmatolay.lirael.presentation.statistics.StatisticsViewModel
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import dev.anmatolay.lirael.presentation.dialog.deletion.DeletionConfirmationViewModel
import dev.anmatolay.lirael.presentation.dialog.exit.ExitConfirmationViewModel
import dev.anmatolay.lirael.presentation.favourites.FavouritesViewModel
import dev.anmatolay.lirael.presentation.onboarding.diet.DietViewModel
import dev.anmatolay.lirael.presentation.onboarding.name.NameViewModel
import dev.anmatolay.lirael.presentation.onboarding.premium.PremiumViewModel
import dev.anmatolay.lirael.presentation.onboarding.welcome.WelcomeViewModel
import dev.anmatolay.lirael.presentation.settings.SettingsViewModel
import dev.anmatolay.lirael.presentation.splash.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainActivityViewModel(get(), get()) }
    factory { SplashViewModel(get(), get()) }
    factory { WelcomeViewModel(get(), get(), get()) }
    factory { NameViewModel(get(), get()) }
    factory { DietViewModel(get(), get()) }
    factory { PremiumViewModel(get(), get(), get()) }
    factory { StatisticsViewModel(get(), get(), get()) }
    factory { RecipesViewModel() }
    factory { FavouritesViewModel() }
    factory { CustomViewModel() }
    factory { ExitConfirmationViewModel(get()) }
    factory { SettingsViewModel(get(), get(), get()) }
    factory { DeletionConfirmationViewModel(get(), get()) }
}

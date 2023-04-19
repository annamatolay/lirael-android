package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.domain.usecase.SaveUserUseCase
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import dev.anmatolay.lirael.domain.usecase.UpdateUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUserUseCase(get(), get()) }
    factory { SaveUserUseCase(get(), get()) }
    factory { UpdateUserUseCase(get(), get()) }
    factory { MonitoringUseCase(get(), get(), get()) }
}

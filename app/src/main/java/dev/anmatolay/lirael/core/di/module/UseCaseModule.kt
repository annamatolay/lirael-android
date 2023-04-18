package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.domain.usecase.CacheUserIdUseCase
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUserUseCase(get()) }
    factory { CacheUserIdUseCase(get()) }
    factory { MonitoringUseCase(get(), get(), get()) }
}

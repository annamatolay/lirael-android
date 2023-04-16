package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.data.local.CacheUserIdUseCase
import dev.anmatolay.lirael.data.local.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUserUseCase(get()) }
    factory { CacheUserIdUseCase(get()) }
    factory { MonitoringUseCase(get(), get(), get()) }
}

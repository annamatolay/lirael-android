package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.data.local.UserIdDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { UserIdDataSource(get()) }
}

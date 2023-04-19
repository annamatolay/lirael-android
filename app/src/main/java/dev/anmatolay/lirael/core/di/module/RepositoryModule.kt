package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepository(get(), get(), get()) }
}

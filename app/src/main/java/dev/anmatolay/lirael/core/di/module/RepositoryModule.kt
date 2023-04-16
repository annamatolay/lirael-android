package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.data.repository.UserCacheRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserCacheRepository(get()) }
}

package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.data.api.SearchRecipeApi
import dev.anmatolay.lirael.data.api.RandomRecipeApi
import dev.anmatolay.lirael.data.local.UserIdDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { UserIdDataSource(get()) }
    factory { RandomRecipeApi(get(), "https://random-recipes.p.rapidapi.com/") }
    factory { SearchRecipeApi(get(), "https://api.api-ninjas.com/") }
}

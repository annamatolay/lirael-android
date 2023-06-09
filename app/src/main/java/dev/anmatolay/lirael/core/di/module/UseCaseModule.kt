package dev.anmatolay.lirael.core.di.module

import dev.anmatolay.lirael.domain.usecase.recipe.GetRandomRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.SearchRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.user.SaveUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import dev.anmatolay.lirael.domain.usecase.cooking.GetCookingStreaks
import dev.anmatolay.lirael.domain.usecase.cooking.GetCookingHistoryUseCase
import dev.anmatolay.lirael.domain.usecase.cooking.PutCookingHistoryUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.GetPresetRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.PopulatePresetRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.DeleteFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.GetAllFavouriteRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.GetFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.recipe.favourite.SaveFavouriteRecipeUseCase
import dev.anmatolay.lirael.domain.usecase.user.DeleteUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.impl.GetUserUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetUserUseCase> { GetUserUseCaseImpl(get(), get()) }
    factory { SaveUserUseCase(get(), get()) }
    factory { UpdateUserUseCase(get(), get(), get()) }
    factory { DeleteUserUseCase(get(), get()) }
    factory { MonitoringUseCase(get(), get(), get()) }
    factory { GetRandomRecipesUseCase(get(), get()) }
    factory { SearchRecipesUseCase(get(), get()) }
    factory { GetFavouriteRecipeUseCase(get(), get()) }
    factory { GetAllFavouriteRecipesUseCase(get(), get()) }
    factory { DeleteFavouriteRecipeUseCase(get(), get()) }
    factory { SaveFavouriteRecipeUseCase(get(), get()) }
    factory { PopulatePresetRecipesUseCase(get(), get()) }
    factory { GetPresetRecipesUseCase(get(), get()) }
    factory { GetCookingHistoryUseCase(get(), get()) }
    factory { PutCookingHistoryUseCase(get(), get()) }
    factory { GetCookingStreaks(get(), get(), get()) }
}

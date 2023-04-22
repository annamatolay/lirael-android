package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.RandomRecipeRepository
import dev.anmatolay.lirael.data.repository.SearchRecipeRepository

class GetRecipesUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: SearchRecipeRepository,
) {

    operator fun invoke(keyword: String) =
        repository.getRecipes(keyword)
            .subscribeOn(schedulerProvider.io())
}

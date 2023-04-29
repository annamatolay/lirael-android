package dev.anmatolay.lirael.domain.usecase.recipe

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.RecipeRepository

class GetRandomRecipesUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: RecipeRepository,
) {

    operator fun invoke() =
        repository.getRecipes()
            .subscribeOn(schedulerProvider.io())
}

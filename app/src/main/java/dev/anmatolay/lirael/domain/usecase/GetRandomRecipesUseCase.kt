package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.RandomRecipeRepository

class GetRandomRecipesUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: RandomRecipeRepository,
) {

    operator fun invoke() =
        repository.getRecipes()
            .subscribeOn(schedulerProvider.io())
}

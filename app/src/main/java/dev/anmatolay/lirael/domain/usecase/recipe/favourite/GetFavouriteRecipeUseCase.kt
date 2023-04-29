package dev.anmatolay.lirael.domain.usecase.recipe.favourite

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.RecipeRepository

class GetFavouriteRecipeUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: RecipeRepository,
) {

    operator fun invoke(title: String) =
        repository.get(title)
            .subscribeOn(schedulerProvider.io())
}

package dev.anmatolay.lirael.domain.usecase.recipe.favourite

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.RecipeRepository
import dev.anmatolay.lirael.domain.model.Recipe

class SaveFavouriteRecipeUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: RecipeRepository,
) {

    operator fun invoke(recipe: Recipe) =
        repository.save(recipe)
            .subscribeOn(schedulerProvider.io())
}

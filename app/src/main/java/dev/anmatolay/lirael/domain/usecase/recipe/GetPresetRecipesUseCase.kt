package dev.anmatolay.lirael.domain.usecase.recipe

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.PresetRecipeRepository
import dev.anmatolay.lirael.domain.model.PresetRecipe

class GetPresetRecipesUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val presetRecipeRepository: PresetRecipeRepository,
) {

    operator fun invoke(category: PresetRecipe.Category) =
        presetRecipeRepository.getAll(category)
            .subscribeOn(schedulerProvider.io())
}

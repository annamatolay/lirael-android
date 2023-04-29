package dev.anmatolay.lirael.domain.usecase.recipe

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.PresetRecipeRepository
import dev.anmatolay.lirael.domain.model.PresetRecipe

class PopulatePresetRecipesUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val presetRecipeRepository: PresetRecipeRepository,
) {

    operator fun invoke(presets: List<PresetRecipe>) =
        presetRecipeRepository.saveAll(presets)
            .subscribeOn(schedulerProvider.io())

    fun isEmpty() =
        presetRecipeRepository.isEmpty()
            .subscribeOn(schedulerProvider.io())
}

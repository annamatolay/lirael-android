package dev.anmatolay.lirael.presentation.recipes

import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.domain.model.PresetRecipe

sealed class RecipesEvent: UiEvent {
    class PresetOnClicked(val category: PresetRecipe.Category): RecipesEvent()
    class SearchRecipe(val keyword: String): RecipesEvent()
}

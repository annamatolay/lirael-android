package dev.anmatolay.lirael.presentation.recipes

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class RecipesEvent: UiEvent {
    class GetPreset(val key: String): RecipesEvent()
    class SearchRecipe(val keyword: String): RecipesEvent()
}

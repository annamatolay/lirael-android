package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.domain.model.Recipe

sealed class CookingStepEvent: UiEvent{
    class CheckIsSavedAsFavourite(val recipeTitle: String?) : CookingStepEvent()

    class OnPositiveButtonClicked(val recipe: Recipe?) : CookingStepEvent()

    object OnNeutralButtonClicked: CookingStepEvent()
}

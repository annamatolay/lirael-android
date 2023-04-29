package dev.anmatolay.lirael.presentation.cooking

import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.domain.model.Recipe

sealed class CookingSummaryEvent: UiEvent {
    class CheckFavouriteSaved(val title: String): CookingSummaryEvent()

    class FavouriteOnClicked(val recipe: Recipe, val isDeletion: Boolean = false): CookingSummaryEvent()
}

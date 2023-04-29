package dev.anmatolay.lirael.presentation.favourites

import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.domain.model.Recipe

sealed class FavouriteEvent : UiEvent {

    class OnDeleteClicked(val recipe: Recipe): FavouriteEvent()
}

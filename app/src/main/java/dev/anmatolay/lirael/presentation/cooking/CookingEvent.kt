package dev.anmatolay.lirael.presentation.cooking

import dev.anmatolay.lirael.core.presentation.UiEvent
import dev.anmatolay.lirael.domain.model.Recipe

sealed class CookingEvent: UiEvent {
    class CoockingStarted(val recipe: Recipe): CookingEvent()
}

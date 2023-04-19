package dev.anmatolay.lirael.presentation.onboarding.diet

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class DietEvent: UiEvent {
    class DietSelected(val id: Int): DietEvent()
}

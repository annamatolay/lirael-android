package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class CookingStepEvent: UiEvent{
    object OnPositiveButtonClicked: CookingStepEvent()
}

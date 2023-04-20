package dev.anmatolay.lirael.presentation.onboarding.premium

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class PremiumEvent: UiEvent {
    object OnPositiveButtonClicked: PremiumEvent()
    object OnNegativeButtonClicked: PremiumEvent()
    object OnNeutralButtonClicked: PremiumEvent()
}

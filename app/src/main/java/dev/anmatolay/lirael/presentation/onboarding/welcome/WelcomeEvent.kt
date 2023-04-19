package dev.anmatolay.lirael.presentation.onboarding.welcome

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class WelcomeEvent: UiEvent {
    object TermAndConditionAccepted: WelcomeEvent()
}

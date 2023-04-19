package dev.anmatolay.lirael.presentation.onboarding.name

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class NameEvent: UiEvent {
    class UpdateUserName(val name: String): NameEvent()
}

package dev.anmatolay.lirael.presentation.settings

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class SettingsEvent: UiEvent {
    class UsernameChanged(val name: String): SettingsEvent()
    object RetryDeleteUserOnClicked: SettingsEvent()
}

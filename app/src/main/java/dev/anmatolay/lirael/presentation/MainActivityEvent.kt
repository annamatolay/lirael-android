package dev.anmatolay.lirael.presentation

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class MainActivityEvent : UiEvent {
    class UiModeChanged(val isDarkModeEnabled: Boolean) : MainActivityEvent()
}

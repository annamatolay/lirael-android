package dev.anmatolay.lirael.presentation.home

import dev.anmatolay.lirael.core.presentation.UiEvent

sealed class HomeEvent: UiEvent {
    object ScreenOnClicked: HomeEvent()
}

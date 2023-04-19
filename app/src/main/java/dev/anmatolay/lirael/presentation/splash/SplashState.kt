package dev.anmatolay.lirael.presentation.splash

import dev.anmatolay.lirael.core.presentation.UiState

data class SplashState(
    val isIdle: Boolean = true,
    val isUserExist: Boolean = false,
) : UiState

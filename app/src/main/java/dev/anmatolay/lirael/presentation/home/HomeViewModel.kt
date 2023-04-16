package dev.anmatolay.lirael.presentation.home

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.presentation.home.HomeEvent
import dev.anmatolay.lirael.presentation.home.HomeState

class HomeViewModel : BaseUdfViewModel<HomeState, HomeEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {
                HomeEvent.ScreenOnClicked -> {
                    val isTextVisible = uiState.value?.isTextVisible ?: false
                    triggerUiStateChange(HomeState(!isTextVisible))
                }
            }
        }.subscribe().disposeOnPause()
    }
}

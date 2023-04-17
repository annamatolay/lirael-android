package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel

class StatisticsViewModel : BaseUdfViewModel<StatisticsState, StatisticsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {
                StatisticsEvent.ScreenOnClicked -> {
                    val isTextVisible = uiState.value?.isTextVisible ?: false
                    triggerUiStateChange(StatisticsState(!isTextVisible))
                }
            }
        }.subscribe().disposeOnPause()
    }
}

package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase

class StatisticsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
): BaseUdfViewModel<StatisticsState, StatisticsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        getUserUseCase()
            .doOnSuccess { triggerUiStateChange(StatisticsState(it.recipeStatistic))}
            .observeOn(schedulerProvider.mainThread())
            .subscribe()
            .disposeOnPause()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {

            }
        }.subscribe().disposeOnPause()
    }
}

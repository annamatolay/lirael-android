package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase
import dev.anmatolay.lirael.presentation.statistics.StatisticsState.Error.STAT_READ_ERROR
import timber.log.Timber

class StatisticsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
): BaseUdfViewModel<StatisticsState, StatisticsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        getUserDataAndTriggerUIStateChange()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {
                StatisticsEvent.RetryGetStatOnClicked -> getUserDataAndTriggerUIStateChange()
            }
        }.subscribe().disposeOnPause()
    }

    private fun getUserDataAndTriggerUIStateChange() {
        getUserUseCase()
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                { triggerUiStateChange(StatisticsState(it.name, it.recipeStatistic)) },
                {
                    Timber.e(it)
                    triggerUiStateChange(StatisticsState(error = STAT_READ_ERROR))
                }
            )
            .disposeOnPause()
    }
}

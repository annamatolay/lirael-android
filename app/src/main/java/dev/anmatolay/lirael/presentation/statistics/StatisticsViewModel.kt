package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.recipe.GetRandomRecipesUseCase
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.presentation.statistics.StatisticsState.Error.RECIPES_READ_ERROR
import dev.anmatolay.lirael.presentation.statistics.StatisticsState.Error.STAT_READ_ERROR
import timber.log.Timber

class StatisticsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
) : BaseUdfViewModel<StatisticsState, StatisticsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        getUserStatisticsAndTriggerUIStateChange()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {
                StatisticsEvent.GetUserStatistics -> getUserStatisticsAndTriggerUIStateChange()
                StatisticsEvent.GetRandomRecipes -> fetchRandomRecipesAndTriggerUIStateChange()
                StatisticsEvent.RetryGetStatOnClicked -> getUserStatisticsAndTriggerUIStateChange()
                StatisticsEvent.RetryGetRandomRecipes -> fetchRandomRecipesAndTriggerUIStateChange()
            }
        }.subscribe().disposeOnPause()
    }

    private fun getUserStatisticsAndTriggerUIStateChange() {
        getUserUseCase()
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                { triggerUiStateChange(StatisticsState.UserDataState(it.name, it.recipeStatistic)) },
                {
                    Timber.e(it)
                    triggerUiStateChange(StatisticsState.ErrorState(error = STAT_READ_ERROR))
                }
            )
            .disposeOnPause()
    }

    private fun fetchRandomRecipesAndTriggerUIStateChange() {
        triggerUiStateChange(StatisticsState.RecipesState())
        getRandomRecipesUseCase()
            .observeOn(schedulerProvider.mainThread())
            .subscribe(
                {
                    Timber.d(it.toString())
                    triggerUiStateChange(StatisticsState.RecipesState(recipes = it, isRecipesLoading = false))
                },
                {
                    Timber.e(it)
                    triggerUiStateChange(StatisticsState.ErrorState(error = RECIPES_READ_ERROR))
                }
            )
            .disposeOnPause()
    }
}

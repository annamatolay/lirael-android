package dev.anmatolay.lirael.presentation.cooking.step

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.State
import dev.anmatolay.lirael.presentation.cooking.step.CookingStepState.Error.USER_STAT_UPDATE_FAILED
import timber.log.Timber

class CookingStepViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseUdfViewModel<CookingStepState, CookingStepEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        doOnUiEventReceived { event ->
            when (event) {
                CookingStepEvent.OnPositiveButtonClicked -> {
                    triggerUiStateChange(CookingStepState(isLoading = true))
                    getUserUseCase()
                        .observeOn(schedulerProvider.mainThread())
                        .map {
                            it.copy(recipeStatistic = it.recipeStatistic.copy(cooked = it.recipeStatistic.cooked + 1))
                        }
                        .flatMapCompletable { updateUserUseCase(it) }
                        .subscribe(
                            { triggerUiStateChange(CookingStepState(isLoading = false, isUpdateDone = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(
                                    CookingStepState(
                                        isLoading = false,
                                        error = USER_STAT_UPDATE_FAILED
                                    )
                                )
                            }
                        )
                        .disposeOnPause()
                }
            }
        }.subscribe().disposeOnPause()
    }
}

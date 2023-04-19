package dev.anmatolay.lirael.presentation.onboarding.name

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.State
import timber.log.Timber

class NameViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseUdfViewModel<NameState, NameEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        triggerUiStateChange(NameState())
    }

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { uiEvent ->
            when (uiEvent) {
                is NameEvent.UpdateUserName -> {
                    triggerUiStateChange(NameState(isLoading = true))
                    updateUserUseCase(uiEvent.name)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(NameState(isUpdateCompleted = true)) },
                            { Timber.e(it) }
                        )
                        .disposeOnPause()
                }
            }
        }.subscribe().disposeOnPause()
    }
}

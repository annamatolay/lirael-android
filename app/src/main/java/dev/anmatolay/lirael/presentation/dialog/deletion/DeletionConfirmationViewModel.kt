package dev.anmatolay.lirael.presentation.dialog.deletion

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.DeleteUserUseCase
import dev.anmatolay.lirael.presentation.settings.SettingsState
import timber.log.Timber

class DeletionConfirmationViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val deleteUserUseCase: DeleteUserUseCase,
) : BaseUdfViewModel<SettingsState, DeletionConfirmationEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        doOnUiEventReceived { event ->
            when (event) {
                is DeletionConfirmationEvent.DeletionConfirmed -> {
                    deleteUserUseCase()
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(SettingsState(isUserDeletionComplete = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(SettingsState(error = SettingsState.Error.USER_DELETION_ERROR))
                            }
                        )
                        .disposeOnPause()
                }
                is DeletionConfirmationEvent.RetryDeleteUserOnClicked ->
                    deleteUserUseCase()
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(SettingsState(isUserDeletionComplete = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(SettingsState(error = SettingsState.Error.USER_DELETION_ERROR))
                            }
                        )
                        .disposeOnPause()
            }
        }.subscribe().disposeOnDestroy()
    }
}

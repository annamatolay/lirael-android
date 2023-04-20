package dev.anmatolay.lirael.presentation.settings

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.DeleteUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.settings.SettingsState.Error.USER_DELETION_ERROR
import timber.log.Timber

class SettingsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : BaseUdfViewModel<SettingsState, SettingsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()
        doOnUiEventReceived { event ->
            when (event) {
                is SettingsEvent.UsernameChanged ->
                    updateUserUseCase(event.name)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe()
                        .disposeOnPause()
                is SettingsEvent.RetryDeleteUserOnClicked ->
                    deleteUserUseCase()
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(SettingsState(isUserDeletionComplete = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(SettingsState(error = USER_DELETION_ERROR))
                            }
                        )
                        .disposeOnPause()
            }
        }.subscribe().disposeOnPause()
    }
}

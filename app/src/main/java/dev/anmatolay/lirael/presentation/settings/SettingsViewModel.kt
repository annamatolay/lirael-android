package dev.anmatolay.lirael.presentation.settings

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.State

class SettingsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseUdfViewModel<State, SettingsEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()
        doOnUiEventReceived { event ->
            when (event) {
                is SettingsEvent.UsernameChanged ->
                    getUserUseCase.getCachedUserIdOrDefault()
                        .flatMapCompletable { updateUserUseCase(User(it, event.name)) }
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe()
                        .disposeOnPause()
            }
        }.subscribe().disposeOnPause()
    }
}

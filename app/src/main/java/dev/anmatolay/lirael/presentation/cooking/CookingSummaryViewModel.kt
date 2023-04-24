package dev.anmatolay.lirael.presentation.cooking

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.State

class CookingSummaryViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseUdfViewModel<State, CookingEvent>() {


}

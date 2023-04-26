package dev.anmatolay.lirael.presentation.cooking

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.user.UpdateUserUseCase
import dev.anmatolay.lirael.presentation.State
import timber.log.Timber

class CookingSummaryViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseUdfViewModel<State, CookingEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        getUserUseCase()
            .observeOn(schedulerProvider.mainThread())
            .flatMapCompletable { updateUserUseCase(it.recipeStatistic.copy(opened = it.recipeStatistic.opened + 1)) }
            .subscribe(
                {},
                { Timber.e(it) }
            )
            .disposeOnDestroy()
    }
}

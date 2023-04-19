package dev.anmatolay.lirael.domain.usecase.user

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository

class DeleteUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) {
    operator fun invoke() =
        repository.delete()
            .subscribeOn(schedulerProvider.io())
}

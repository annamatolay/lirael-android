package dev.anmatolay.lirael.domain.usecase.user

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository

class GetUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) {

    fun getCachedUserIdOrDefault() = repository.getCachedUserIdOrDefault()

    operator fun invoke() =
        repository.getUserOrDefault()
            .subscribeOn(schedulerProvider.io())
}

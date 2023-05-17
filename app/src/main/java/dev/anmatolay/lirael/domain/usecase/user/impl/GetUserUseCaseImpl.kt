package dev.anmatolay.lirael.domain.usecase.user.impl

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase

class GetUserUseCaseImpl(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) : GetUserUseCase {

    override fun getCachedUserIdOrDefault() = repository.getCachedUserIdOrDefault()

    override operator fun invoke() =
        repository.getUserOrDefault()
            .subscribeOn(schedulerProvider.io())
}

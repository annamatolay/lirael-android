package dev.anmatolay.lirael.domain.usecase.user

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository

class SaveUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) {

    operator fun invoke(id: String) =
        repository.cache(id)
            .andThen(repository.save(id))
            .subscribeOn(schedulerProvider.io())
}

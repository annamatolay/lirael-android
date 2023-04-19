package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository
import dev.anmatolay.lirael.domain.model.User

class SaveUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) {

    operator fun invoke(id: String) =
        repository.cache(id)
            .andThen(repository.save(User(id)))
            .subscribeOn(schedulerProvider.io())

    operator fun invoke(user: User) =
        repository.save(user)
            .subscribeOn(schedulerProvider.io())
}

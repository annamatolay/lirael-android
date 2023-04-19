package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository
import dev.anmatolay.lirael.domain.model.User

class UpdateUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
) {

    operator fun invoke(name: String) =
        repository.update(name)
            .subscribeOn(schedulerProvider.io())

    operator fun invoke(recipeStatistic: User.RecipeStatistic) =
        repository.update(recipeStatistic)
            .subscribeOn(schedulerProvider.io())
}

package dev.anmatolay.lirael.domain.usecase.user

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.data.repository.UserRepository
import dev.anmatolay.lirael.domain.model.User
import io.reactivex.rxjava3.core.Completable

class UpdateUserUseCase(
    private val schedulerProvider: SchedulerProvider,
    private val repository: UserRepository,
    private val getUserUseCase: GetUserUseCase,
) {

    operator fun invoke(name: String) =
        repository.update(name)
            .subscribeOn(schedulerProvider.io())

    operator fun invoke(user: User) =
        repository.update(user)
            .subscribeOn(schedulerProvider.io())

    fun updateRecipeSavedStat(modifier: Int): Completable =
        getUserUseCase()
            .map { it.copy(recipeStatistic = it.recipeStatistic.copy(saved = it.recipeStatistic.saved + modifier)) }
            .flatMapCompletable { invoke(it) }
}

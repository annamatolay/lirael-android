package dev.anmatolay.lirael.domain.usecase.user

import dev.anmatolay.lirael.domain.model.User
import io.reactivex.rxjava3.core.Single

interface GetUserUseCase {

    fun getCachedUserIdOrDefault(): Single<String>

    operator fun invoke(): Single<User>
}

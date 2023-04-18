package dev.anmatolay.lirael.domain.usecase

import dev.anmatolay.lirael.data.repository.UserCacheRepository

class GetUserUseCase(private val repository: UserCacheRepository) {

    operator fun invoke() = repository.getCachedOrDefaultUser()
}

package dev.anmatolay.lirael.data.local

import dev.anmatolay.lirael.data.repository.UserCacheRepository

class CacheUserIdUseCase(private val repository: UserCacheRepository) {

    operator fun invoke(id: String) = repository.cacheUserId(id)
}

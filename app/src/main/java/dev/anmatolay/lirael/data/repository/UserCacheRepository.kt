package dev.anmatolay.lirael.data.repository

import dev.anmatolay.lirael.data.local.UserIdDataSource
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.Constants
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserCacheRepository(private val dataSource: UserIdDataSource) {

    fun getCachedOrDefaultUser(): Single<User> =
        dataSource.getUserId()
            .defaultIfEmpty(Constants.USER_DEFAULT_ID)
            .map { User(it) }

    fun cacheUserId(id: String): Completable =
        Completable.fromAction { dataSource.putUserId(id) }
}

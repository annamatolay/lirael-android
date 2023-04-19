package dev.anmatolay.lirael.data.repository

import android.content.res.Resources
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.data.dao.UserDao
import dev.anmatolay.lirael.data.local.UserIdDataSource
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.util.Constants
import dev.anmatolay.lirael.util.extension.onEmptyResultSetExceptionResumeNext
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(
    private val dataSource: UserIdDataSource,
    private val userDao: UserDao,
    private val resources: Resources,
) {

    fun getCachedUserIdOrDefault(): Single<String> =
        dataSource.getUserId()
            .defaultIfEmpty(Constants.USER_DEFAULT_ID)

    fun getUserOrDefault(): Single<User> =
        dataSource.getUserId()
            .defaultIfEmpty(Constants.USER_DEFAULT_ID)
            .flatMap { userDao.read(it) }
            .onEmptyResultSetExceptionResumeNext(createDefaultUser())

    fun cache(id: String): Completable =
        Completable.fromAction { dataSource.putUserId(id) }

    fun save(id: String) = userDao.create(User(id))

    fun update(name: String): Completable =
        getCachedUserIdOrDefault()
            .flatMap { userDao.read(it) }
            .flatMapCompletable { userDao.update(it.copy(name = name)) }

    fun update(recipeStatistic: User.RecipeStatistic): Completable =
        getCachedUserIdOrDefault()
            .flatMap { userDao.read(it) }
            .flatMapCompletable { userDao.update(it.copy(recipeStatistic = recipeStatistic)) }

    fun delete() = userDao.deleteAll()

    private fun createDefaultUser() =
        User(Constants.USER_DEFAULT_ID, resources.getString(R.string.user_default_name))
}

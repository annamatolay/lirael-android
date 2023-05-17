package dev.anmatolay.lirael.domain.usecase.user.impl

import android.content.res.Resources
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.util.Constants
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.inject

class FakeGetUserUseCaseImpl(var isDefaultUser: Boolean) : GetUserUseCase {

    private val resources: Resources by inject(Resources::class.java)
    private val userId = if (isDefaultUser) Constants.USER_DEFAULT_ID else USER_TEST_ID
    private val user = User(userId, resources.getString(R.string.user_default_name))

    override fun getCachedUserIdOrDefault() = Single.just(userId)

    override operator fun invoke() = Single.just(user)

    companion object {
        const val USER_TEST_ID = "USER_TEST_ID"
    }
}

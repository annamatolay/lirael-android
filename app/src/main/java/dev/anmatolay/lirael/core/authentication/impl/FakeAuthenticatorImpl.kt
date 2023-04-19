package dev.anmatolay.lirael.core.authentication.impl

import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.UnknownAuthErrorException
import dev.anmatolay.lirael.core.authentication.UserProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class FakeAuthenticatorImpl : Authenticator() {

    var isSuccessful: Boolean = false
    var userId = "null"

    override fun signInAnonymously() = Single.create<String> { emitter ->
        if (isSuccessful) {
            userProvider = UserProvider.FakeUser(userId)
            emitter.onSuccess(userId)
        } else {
            emitter.onError(
                UnknownAuthErrorException(
                    isTaskSuccessful = false,
                    isCurrentUserNull = true,
                )
            )
        }
    }
}

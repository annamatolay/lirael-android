package dev.anmatolay.lirael.core.authentication.impl

import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.UnknownAuthErrorException
import dev.anmatolay.lirael.core.authentication.UserProvider
import io.reactivex.rxjava3.core.Completable

class FakeAuthenticatorImpl : Authenticator() {

    var isSuccessful: Boolean = false
    var userId = "null"

    override fun signInAnonymously() = Completable.create { emitter ->
        if (isSuccessful) {
            userProvider = UserProvider.FakeUser(userId)
            emitter.onComplete()
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

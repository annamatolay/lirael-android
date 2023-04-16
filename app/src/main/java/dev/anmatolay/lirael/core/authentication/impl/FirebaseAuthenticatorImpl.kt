package dev.anmatolay.lirael.core.authentication.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.authentication.UnknownAuthErrorException
import dev.anmatolay.lirael.core.authentication.UserProvider
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class FirebaseAuthenticatorImpl(
    private val firebaseAuth: FirebaseAuth,
) : Authenticator() {

    override fun signInAnonymously() = Completable.create { emitter ->
        firebaseAuth.signInAnonymously()
            .addOnCompleteListener { task ->
                val currentUser = firebaseAuth.currentUser
                if (task.isSuccessful && currentUser != null) {
                    userProvider = UserProvider.FirebaseUser(currentUser)
                    Timber
                        .tag(TAG)
                        .i("User anonymously signed in with ID: ${currentUser.uid}")
                    emitter.onComplete()
                } else {
                    val exception = task.exception
                    Timber.tag(TAG).e(exception)
                    emitter.onError(
                        exception ?: UnknownAuthErrorException(
                            task.isSuccessful,
                            currentUser.isNull(),
                        )
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitter.onError(exception)
                Timber.tag(TAG).e(exception)
            }
    }

    companion object {
        private const val TAG = "Firebase Authenticator"
    }
}

private fun FirebaseUser?.isNull(): Boolean = this == null

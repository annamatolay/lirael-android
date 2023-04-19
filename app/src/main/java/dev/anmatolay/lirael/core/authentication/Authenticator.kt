package dev.anmatolay.lirael.core.authentication

import dev.anmatolay.lirael.domain.usecase.SaveUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import io.reactivex.rxjava3.core.Completable
import org.koin.java.KoinJavaComponent.inject
import kotlin.properties.Delegates

abstract class Authenticator {

    private val saveUserUseCase by inject<SaveUserUseCase>(SaveUserUseCase::class.java)
    private val monitoringUseCase by inject<MonitoringUseCase>(MonitoringUseCase::class.java)

    protected var userProvider:
            UserProvider? by Delegates.observable(null) { _, _, userProvider ->
        userProvider.getUserId()?.let { userId ->
            saveUserUseCase(userId)
                .subscribe()
                .dispose()
            monitoringUseCase.setUpAnalyticsAndLogging(userId)
        }
    }

    abstract fun signInAnonymously(): Completable

    private fun UserProvider?.getUserId() =
        when (this) {
            is UserProvider.FakeUser -> this.userId
            is UserProvider.FirebaseUser -> this.user.uid
            null -> null
        }
}

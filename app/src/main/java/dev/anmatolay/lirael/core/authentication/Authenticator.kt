package dev.anmatolay.lirael.core.authentication

import dev.anmatolay.lirael.data.local.CacheUserIdUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import io.reactivex.rxjava3.core.Completable
import org.koin.java.KoinJavaComponent.inject
import kotlin.properties.Delegates

abstract class Authenticator {

    private val cacheUserIdUseCase by inject<CacheUserIdUseCase>(CacheUserIdUseCase::class.java)
    private val monitoringUseCase by inject<MonitoringUseCase>(MonitoringUseCase::class.java)

    protected var userProvider:
            UserProvider? by Delegates.observable(null) { _, _, userProvider ->
        userProvider.getUserId()?.let { userId ->
            cacheUserIdUseCase(userId)
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

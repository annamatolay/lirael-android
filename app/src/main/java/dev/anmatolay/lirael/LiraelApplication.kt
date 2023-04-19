package dev.anmatolay.lirael

import android.app.Application
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.di.KoinInitializer
import dev.anmatolay.lirael.domain.usecase.GetUserUseCase
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import dev.anmatolay.lirael.util.Constants
import org.koin.android.ext.android.inject
import timber.log.Timber

class LiraelApplication : Application() {

    private val authenticator by inject<Authenticator>()
    private val getUserUseCase by inject<GetUserUseCase>()
    private val monitoringUseCase by inject<MonitoringUseCase>()

    private var userId: String? = null

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.init(this)

        monitoringUseCase.isCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        getUserUseCase.getCachedUserIdOrDefault()
            .doOnSuccess { userId = it }
            .subscribe()
            .dispose()

        monitoringUseCase.setUpAnalyticsAndLogging(userId)

        if (userId == Constants.USER_DEFAULT_ID) {
            monitoringUseCase.setUserProperties()
            authenticator.signInAnonymously()
                .subscribe(
                    { Timber.i("Anonymous sign in completed!") },
                    { Timber.tag("App init - signInAnonymously").e(it) }
                )
                .dispose()
        }
    }
}

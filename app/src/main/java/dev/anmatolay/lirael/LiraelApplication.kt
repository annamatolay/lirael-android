package dev.anmatolay.lirael

import android.app.Application
import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.di.KoinInitializer
import dev.anmatolay.lirael.data.local.GetUserUseCase
import dev.anmatolay.lirael.domain.model.User
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import org.koin.android.ext.android.inject
import timber.log.Timber

class LiraelApplication : Application() {

    private val authenticator by inject<Authenticator>()
    private val getUserUseCase by inject<GetUserUseCase>()
    private val monitoringUseCase by inject<MonitoringUseCase>()

    private var user: User? = null

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.init(this)

        monitoringUseCase.isCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        getUserUseCase()
            .doOnSuccess { user = it }
            .subscribe()
            .dispose()

        monitoringUseCase.setUpAnalyticsAndLogging(user?.id)

        if (user?.id == null) {
            monitoringUseCase.setUserProperties()
            authenticator.signInAnonymously()
                .doOnError { Timber.tag("App init - signInAnonymously").e(it) }
                .subscribe()
                .dispose()
        }
    }
}

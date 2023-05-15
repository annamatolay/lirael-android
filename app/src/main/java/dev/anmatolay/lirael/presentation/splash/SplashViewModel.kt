package dev.anmatolay.lirael.presentation.splash

import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.util.Constants
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getUserUseCase: GetUserUseCase,
) : BaseUdfViewModel<SplashState, SplashEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        triggerUiStateChange(SplashState(isIdle = true))
    }

    override fun onViewResumed() {
        super.onViewResumed()

        val timerObservable =
            Observable.timer(SPLASH_DELAY, TimeUnit.SECONDS)
                .observeOn(schedulerProvider.mainThread())

        val userObservable =
            getUserUseCase()
                .observeOn(schedulerProvider.mainThread())
                .toObservable()

        Observable
            .combineLatest(timerObservable, userObservable) { _, user ->
                triggerUiStateChange(SplashState(isIdle = false, isUserExist = user.id != Constants.USER_DEFAULT_ID))
            }
            .doOnError {
                Timber.e(it)
                triggerUiStateChange(SplashState(isIdle = true, isUserExist = false))
            }
            .subscribe()
            .disposeOnPause()
    }

    companion object {
        private val SPLASH_DELAY =
            if (BuildConfig.DEBUG)
                0L
            else
                3L
    }
}

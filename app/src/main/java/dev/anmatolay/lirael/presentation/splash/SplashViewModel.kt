package dev.anmatolay.lirael.presentation.splash

import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.presentation.splash.SplashEvent
import dev.anmatolay.lirael.presentation.splash.SplashState
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel(
    private val schedulerProvider: SchedulerProvider,
) : BaseUdfViewModel<SplashState, SplashEvent>() {

    override fun onViewCreated() {
        super.onViewCreated()

        navigateToHomeAfterThreeSecond()
    }

    private fun navigateToHomeAfterThreeSecond() {
        Observable.timer(3L, TimeUnit.SECONDS)
            .observeOn(schedulerProvider.mainThread())
            .subscribe { triggerUiStateChange(SplashState(isIdle = false)) }
            .disposeOnDestroy()
    }
}

package dev.anmatolay.lirael.presentation.onboarding.welcome

import dev.anmatolay.lirael.core.authentication.Authenticator
import dev.anmatolay.lirael.core.presentation.BaseUdfViewModel
import dev.anmatolay.lirael.core.threading.SchedulerProvider
import dev.anmatolay.lirael.domain.usecase.user.SaveUserUseCase
import dev.anmatolay.lirael.presentation.onboarding.welcome.WelcomeState.Error.USER_CREATION_ERROR
import timber.log.Timber

class WelcomeViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val authenticator: Authenticator,
    private val saveUserUseCase: SaveUserUseCase,
) : BaseUdfViewModel<WelcomeState, WelcomeEvent>() {

    override fun onViewResumed() {
        super.onViewResumed()

        triggerUiStateChange(WelcomeState())

        doOnUiEventReceived { event ->
            when (event) {
                is WelcomeEvent.TermAndConditionAccepted -> {
                    triggerUiStateChange(WelcomeState(isLoading = true))
                    authenticator.signInAnonymously()
                        .flatMapCompletable { saveUserUseCase(it) }
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe(
                            { triggerUiStateChange(WelcomeState(isLoading = false, isUserCreationCompleted = true)) },
                            {
                                Timber.e(it)
                                triggerUiStateChange(WelcomeState(isLoading = false, error = USER_CREATION_ERROR))
                            }
                        )
                        .disposeOnPause()
                }
            }
        }.subscribe().disposeOnPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        triggerUiStateChange(WelcomeState())
    }
}

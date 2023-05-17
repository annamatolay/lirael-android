package dev.anmatolay.lirael.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.presentation.screen.MainScreen
import dev.anmatolay.lirael.presentation.screen.OnboardingScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OnboardingFlowTest : InstrumentedTest(isDefaultUser = true) {

    @Test
    fun openApp_userDoesNotExist_onboardingFlow() {
        onScreen<MainScreen> {
            toolbar.isGone()
            snackbarContainer.isVisible()
            bottomNav.isGone()
        }

        onScreen<OnboardingScreen> {
            title.hasText(R.string.onboarding_welcome_title)
            termsSwitch.click()
            title.hasText(R.string.onboarding_name_title)
            skipButton.click()
            title.hasText(R.string.onboarding_diet_title)
            nextButton.click()
            title.hasText(R.string.onboarding_premium_title)
            positiveButton.click()
        }

        onScreen<MainScreen> {
            toolbar {
                isVisible()
                hasTitle(R.string.title_statistics)
            }
            snackbarContainer.isVisible()
            bottomNav.isVisible()
        }
    }
}

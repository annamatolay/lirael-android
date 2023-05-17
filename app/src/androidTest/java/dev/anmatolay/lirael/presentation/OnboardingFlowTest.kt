package dev.anmatolay.lirael.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.presentation.screen.MainScreen
import dev.anmatolay.lirael.presentation.screen.OnboardingScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OnboardingFlowTest : InstrumentedTest() {

    @Test
    fun openApp_userDoesNotExist_onboardingFlow() {
        onScreen<MainScreen> {
            toolbar.isGone()
            snackbarContainer.isVisible()
            bottomNav.isGone()
        }

        onScreen<OnboardingScreen> {
            termsSwitch.click()
            Thread.sleep(3000)
        }
    }
}

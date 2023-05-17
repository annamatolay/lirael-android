package dev.anmatolay.lirael.presentation.onboarding

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.core.kakaocup.hasFont
import dev.anmatolay.lirael.presentation.onboarding.welcome.WelcomeFragment
import dev.anmatolay.lirael.presentation.screen.OnboardingScreen
import io.github.kakaocup.kakao.screen.Screen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeFragmentTest: InstrumentedTest(launchActivity = false) {

    @Test
    fun textsAreCorrect_TermsOpen_NavigateToNameFragment (){

        launchFragmentInContainer<WelcomeFragment>(
            themeResId = R.style.Theme_Lirael,
            initialState = Lifecycle.State.STARTED
        )


        Screen.onScreen<OnboardingScreen> {
            title {
                isVisible()
                hasText(R.string.onboarding_welcome_title)
                hasTextSize(35)
                hasFont(R.font.ubuntu_regular)
            }
            subtitle {
                isVisible()
                hasText(R.string.onboarding_welcome_subtitle)
                hasTextSize(16)
                hasFont(R.font.ubuntu_medium)
            }
            description {
                isVisible()
                hasText(R.string.onboarding_welcome_description)
                hasTextSize(16)
                hasFont(R.font.ubuntu_regular)
            }
            image.isVisible()
        }
    }
}

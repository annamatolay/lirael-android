package dev.anmatolay.lirael.presentation.splash

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.core.kakaocup.hasFont
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashFragmentTest : InstrumentedTest(launchActivity = false) {

    @Test
    fun textsAreStyledCorrectly() {
        launchFragmentInContainer<SplashFragment>(
            themeResId = R.style.Theme_Lirael,
            initialState = Lifecycle.State.STARTED
        )

        onScreen<SplashScreen> {
            appTitle.run {
                isVisible()
                hasTextSize(99)
                hasTextColor(R.color.white)
                hasFont(R.font.ubuntu_light)
            }
            description.run {
                isVisible()
                hasTextSize(21)
                hasTextColor(R.color.white)
                hasFont(R.font.ubuntu_medium)
            }
        }
    }
}

package dev.anmatolay.lirael.presentation.splash

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

class SplashScreen : Screen<SplashScreen>() {
    val appTitle = KTextView {
        withId(R.id.appTitle)
        withText(R.string.app_name)
    }
    val description = KTextView {
        withId(R.id.description)
        withText(R.string.app_description)
    }
}

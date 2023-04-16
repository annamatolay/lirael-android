package dev.anmatolay.lirael.presentation.splash

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

class SplashScreen: Screen<SplashScreen>() {
    val title = KTextView { withId(R.id.appTitle) }
}

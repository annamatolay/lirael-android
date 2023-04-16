package dev.anmatolay.lirael.presentation.home

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

class HomeScreen: Screen<HomeScreen>() {
    val text = KTextView { withId(R.id.text) }
}

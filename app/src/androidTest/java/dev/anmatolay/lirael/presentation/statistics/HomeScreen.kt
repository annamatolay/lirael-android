package dev.anmatolay.lirael.presentation.statistics

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

class HomeScreen: Screen<HomeScreen>() {
    val text = KTextView { withId(R.id.text) }
}

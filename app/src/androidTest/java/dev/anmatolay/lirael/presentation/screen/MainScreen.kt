package dev.anmatolay.lirael.presentation.screen

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.toolbar.KToolbar

class MainScreen : Screen<MainScreen>() {
    val toolbar = KToolbar { withId(R.id.toolbar) }
    val snackbarContainer = KView { withId(R.id.snackbar_container) }
    val bottomNav = KBottomNavigationView { withId(R.id.bottom_nav_view) }
}

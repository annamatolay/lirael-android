package dev.anmatolay.lirael.presentation.screen

import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.drawer.KDrawerView
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.navigation.KNavigationView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.toolbar.KToolbar

class MainScreen : Screen<MainScreen>() {
    val toolbar = KToolbar { withId(R.id.toolbar) }
    val navigation = KNavigationView { withId(R.id.navigation) }
    val drawer = KDrawerView { withId(R.id.drawer_layout) }
    val snackbarContainer = KView { withId(R.id.snackbar_container) }
    val bottomNav = KBottomNavigationView { withId(R.id.bottom_nav_view) }

    @Suppress("unused") // Waiting for fix
    val navHeader = KView { withId(R.id.nav_header) }
    val navTitle = KTextView { withId(R.id.nav_title) }
    val navImage = KImageView { withId(R.id.nav_image) }
    val navDescription = KTextView { withId(R.id.nav_description) }
    val navItemAbout = KView { withText(R.string.title_about) }
    val navItemTitle = KView { withText(R.string.title_premium) }
    val navItemSubscription = KView { withText(R.string.title_subscription) }
    val navItemNutrition = KView { withText(R.string.title_nutrition) }

    val menuItemSettings = KView { withId(R.id.settings_item) }
    val menuItemUIMode = KView { withId(R.id.ui_mode_item) }
}

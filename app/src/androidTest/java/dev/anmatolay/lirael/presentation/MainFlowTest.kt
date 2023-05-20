package dev.anmatolay.lirael.presentation

import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.core.kakaocup.hasFont
import dev.anmatolay.lirael.core.kakaocup.hasTitleColor
import dev.anmatolay.lirael.core.kakaocup.isAllCaps
import dev.anmatolay.lirael.presentation.screen.MainScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainFlowTest : InstrumentedTest() {

    @Test
    fun openApp_userExists_navDrawerVisible() {
        onScreen<MainScreen> {
            navigation.isInvisible()
            drawer.open()
            navigation.isVisible()

            // FIXME
            // navHeader.hasDrawable(R.mipmap.ic_launcher_background)
            navTitle {
                isVisible()
                hasGravity(horizontalGravity = Gravity.START, verticalGravity = Gravity.CENTER_VERTICAL)
                hasText(R.string.app_name)
                hasTextColor(R.color.white)
                hasTextSize(49)
                hasFont(R.font.ubuntu_regular)
            }
            navImage.hasDrawable(R.mipmap.ic_launcher_foreground)
            navDescription {
                isVisible()
                hasText(R.string.app_description)
                hasTextColor(R.color.white_400)
                hasTextSize(10)
                hasFont(R.font.ubuntu_medium)
                isAllCaps()
            }
            navItemAbout.isVisible()
            navItemTitle.isVisible()
            navItemSubscription.isVisible()
            navItemNutrition.isVisible()
        }
    }

    @Test
    fun openApp_userExists_mainFlow() {
        onScreen<MainScreen> {
            toolbar.isVisible()
            snackbarContainer.isVisible()
            bottomNav.isVisible()

            isBottomNavItemSelectedAndToolbarTitleCorrect("statistics")

            bottomNav.setSelectedItem(R.id.recipes_fragment)

            isBottomNavItemSelectedAndToolbarTitleCorrect("recipes")

            bottomNav.setSelectedItem(R.id.favourites_fragment)

            isBottomNavItemSelectedAndToolbarTitleCorrect("favourites")

            bottomNav.setSelectedItem(R.id.statistics_fragment)
            drawer.open()
            navItemAbout.click()

            toolbar.hasTitle(R.string.title_about)

            menuItemSettings.click()

            toolbar.hasTitle(R.string.title_settings)

            pressBack()
            toolbar.hasTitleColor(R.color.black_toolbar_title)
            menuItemUIMode.click()
            toolbar.hasTitleColor(R.color.white)
            menuItemUIMode.click()
        }
    }

    private fun MainScreen.isBottomNavItemSelectedAndToolbarTitleCorrect(identifier: String) {
        val bottomNavItemId =
            context.resources.getIdentifier(identifier + "_fragment", "id", context.packageName)
        bottomNav.hasSelectedItem(bottomNavItemId)

        val stringResId =
            context.resources.getIdentifier("title_$identifier", "string", context.packageName)
        toolbar.hasTitle(stringResId)
    }
}

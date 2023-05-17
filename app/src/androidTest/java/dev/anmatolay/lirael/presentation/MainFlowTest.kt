package dev.anmatolay.lirael.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.InstrumentedTest
import dev.anmatolay.lirael.presentation.screen.MainScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainFlowTest : InstrumentedTest() {

    @Test
    fun openApp_userExists_mainFlow() {
        onScreen<MainScreen> {
            toolbar.isVisible()
            snackbarContainer.isVisible()
            bottomNav.isVisible()

            isBottomNavItemSelectedAndToolBarTitleCorrect("statistics")

            bottomNav.setSelectedItem(R.id.recipes_fragment)

            isBottomNavItemSelectedAndToolBarTitleCorrect("recipes")

            bottomNav.setSelectedItem(R.id.favourites_fragment)

            isBottomNavItemSelectedAndToolBarTitleCorrect("favourites")
        }
    }

    private fun MainScreen.isBottomNavItemSelectedAndToolBarTitleCorrect(identifier: String) {
        val bottomNavItemId =
            context.resources.getIdentifier(identifier + "_fragment", "id", context.packageName)
        bottomNav.hasSelectedItem(bottomNavItemId)

        val stringResId =
            context.resources.getIdentifier("title_$identifier", "string", context.packageName)
        toolbar.hasTitle(stringResId)
    }
}

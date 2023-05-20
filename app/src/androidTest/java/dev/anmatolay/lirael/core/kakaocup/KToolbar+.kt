package dev.anmatolay.lirael.core.kakaocup

import androidx.annotation.ColorRes
import androidx.test.espresso.assertion.ViewAssertions
import dev.anmatolay.lirael.core.kakaocup.matcher.ToolbarTitleColorMatcher
import io.github.kakaocup.kakao.toolbar.KToolbar

fun KToolbar.hasTitleColor(@ColorRes resId: Int) {
    view.check(
        ViewAssertions.matches(
            ToolbarTitleColorMatcher(resId)
        )
    )
}
